package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Map;

public class OpenWindowReplacer extends BasePacketAdapter{
    private Field windowTypeField = null;

    private Object anvilType = null;

    public OpenWindowReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.OPEN_WINDOW);

        Class<?> packetClass = PacketType.Play.Server.OPEN_WINDOW.getPacketClass();
        for (Field declaredField : packetClass.getDeclaredFields()) {
            if (declaredField.getType() != int.class && declaredField.getType() != MinecraftReflection.getIChatBaseComponentClass()) {
                this.windowTypeField = declaredField;
                this.windowTypeField.setAccessible(true);
                try {
                    this.anvilType = this.windowTypeField.getType().getDeclaredField("h").get(null);
                } catch (NoSuchFieldException|IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player user = event.getPlayer();
        if (user == null)
            return;
        PacketContainer packet = event.getPacket();
        StructureModifier<WrappedChatComponent> wrappedChatComponentStructureModifier = packet.getChatComponents();
        WrappedChatComponent wrappedChatComponent = wrappedChatComponentStructureModifier.read(0);
        wrappedChatComponent.setJson(replace(user, wrappedChatComponent.getJson()));
        wrappedChatComponentStructureModifier.write(0, wrappedChatComponent);
    }
}
