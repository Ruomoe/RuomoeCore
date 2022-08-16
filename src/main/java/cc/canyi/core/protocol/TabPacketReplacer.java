package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.util.Map;

public class TabPacketReplacer extends BasePacketAdapter{

    public TabPacketReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
    }

    @Override
    public void onReplace(final PacketEvent event) {
        final PacketContainer packet = event.getPacket();
        replace(event.getPlayer(), packet, 0);
        replace(event.getPlayer(), packet, 1);
    }

    private void replace(final Player player, final PacketContainer packet, final int id) {
        final WrappedChatComponent wcpHeader = packet.getChatComponents().read(id);
        if (wcpHeader != null) {
            wcpHeader.setJson(replace(player, wcpHeader.getJson()));
            packet.getChatComponents().write(id, wcpHeader);
        }
    }
}

