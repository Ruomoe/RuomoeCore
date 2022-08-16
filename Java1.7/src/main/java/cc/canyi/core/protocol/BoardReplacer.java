package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

import java.util.Map;

public class BoardReplacer extends BasePacketAdapter{
    public BoardReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.SCOREBOARD_OBJECTIVE, PacketType.Play.Server.SCOREBOARD_SCORE, PacketType.Play.Server.SCOREBOARD_TEAM);
    }

    @Override
    public void onReplace(final PacketEvent event) {
        StructureModifier<String> strings = event.getPacket().getStrings();
        for (int i = 0; i < strings.size(); i++) {
            strings.write(i, replace(event.getPlayer(), strings.read(i)));
        }
    }
}
