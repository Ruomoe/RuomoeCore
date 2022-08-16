package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Map;

public class EntityPacketReplacer extends BasePacketAdapter{

    public EntityPacketReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.ENTITY_METADATA);
    }

    @Override
    public void onReplace(final PacketEvent event) {
        StructureModifier<Entity> modify = event.getPacket().getEntityModifier(event);
        modify.getValues().forEach(entity -> entity.getCustomName());
        final List<WrappedWatchableObject> list = event.getPacket().getWatchableCollectionModifier().read(0);
        if (list == null) { return; }
        for (int i = 0; i < list.size(); ++i) {
            final WrappedWatchableObject wwo = list.get(i);
            if (wwo.getIndex() == 2) {
                wwo.setValue(replace(event.getPlayer(), String.valueOf(wwo.getValue())));
            }
            list.set(i, wwo);
        }
    }
}
