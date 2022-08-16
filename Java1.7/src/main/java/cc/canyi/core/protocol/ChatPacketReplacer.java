package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import java.util.Map;

public class ChatPacketReplacer extends BasePacketAdapter{

    public ChatPacketReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.CHAT);
    }

    @Override
    public void onReplace(PacketEvent event) {
        final WrappedChatComponent wcc = event.getPacket().getChatComponents().read(0);
        if (wcc == null) { return; }
        wcc.setJson(replace(event.getPlayer(), wcc.getJson()));
        event.getPacket().getChatComponents().writeSafely(0, wcc);
    }
}
