package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.util.Map;

public class KickReplacer extends BasePacketAdapter{
    public KickReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.KICK_DISCONNECT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player user = event.getPlayer();
        if (user == null)
            return;
        PacketContainer packet = event.getPacket();
        StructureModifier<WrappedChatComponent> chatComponents = packet.getChatComponents();
        WrappedChatComponent wrappedChatComponent = chatComponents.read(0);
        wrappedChatComponent.setJson(replace(user, wrappedChatComponent.getJson()));
//        WrappedChatComponent replaced = getReplacedJsonWrappedComponent(packetEvent, user, this.listenType, json, this.filter);
        chatComponents.write(0, wrappedChatComponent);
    }
}
