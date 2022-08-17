package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.BukkitConverters;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class EntityPacketReplacer extends BasePacketAdapter {

    public EntityPacketReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.ENTITY_METADATA);
    }

    @Override
    public void onReplace(final PacketEvent event) {
//        StructureModifier<Entity> modify = event.getPacket().getEntityModifier(event);
//        modify.getValues().forEach(entity -> entity.getCustomName());
//        final List<WrappedWatchableObject> list = event.getPacket().getWatchableCollectionModifier().read(0);
//        if (list == null) { return; }
//        for (int i = 0; i < list.size(); ++i) {
//            final WrappedWatchableObject wwo = list.get(i);
//            if (wwo.getIndex() == 2) {
//                wwo.setValue(replace(event.getPlayer(), String.valueOf(wwo.getValue())));
//            }
//            list.set(i, wwo);
//        }

        PacketContainer packet;
        Player user = event.getPlayer();
        if (user == null)
            return;
        PacketContainer ognPacket = event.getPacket();
        try {
            if (ognPacket.getEntityModifier(event).read(0) == null)
                return;
            packet = ognPacket.deepClone();
        } catch (RuntimeException e) {
            return;
        }
        List<WrappedWatchableObject> metadataList = packet.getWatchableCollectionModifier().read(0);
        if (metadataList != null) {
            for (WrappedWatchableObject watchableObject : metadataList) {
                Object getValue = watchableObject.getValue();

                WrappedChatComponent wrappedChatComponent;
                if (MinecraftReflection.getIChatBaseComponentClass().isInstance(getValue)) {
                    wrappedChatComponent = WrappedChatComponent.fromHandle(getValue);
                } else if (getValue instanceof WrappedChatComponent) {
                    wrappedChatComponent = (WrappedChatComponent) getValue;
                } else {
                    continue;
                }

                String replacedJson = replace(user, wrappedChatComponent.getJson());
//                        getReplacedJson(event, user, this.listenType, wrappedChatComponent.getJson(), this.filter);
                if (replacedJson != null) {
                    wrappedChatComponent.setJson(replacedJson);
                    watchableObject.setValue(wrappedChatComponent.getHandle());
                    continue;
                }

                if (BukkitConverters.getItemStackConverter().getSpecificType().isInstance(getValue)) {
                    ItemStack itemStack = BukkitConverters.getItemStackConverter().getSpecific(getValue);
                    replaceItem(user, itemStack);
//                    replaceItemStack(packetEvent, user, this.listenType, itemStack, this.filter);
                }
            }
            event.setPacket(packet);
        }
    }
}
