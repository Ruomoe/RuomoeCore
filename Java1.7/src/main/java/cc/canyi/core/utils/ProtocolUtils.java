package cc.canyi.core.utils;

import cc.canyi.core.plugin.BukkitPlugin;
import cc.canyi.core.protocol.*;

import java.util.Map;

public class ProtocolUtils {
    public static BasePacketAdapter createPacketAdapter(BukkitPlugin bukkitPlugin, Map<Object, Object> replaceMap, boolean replacePAPI, EnumPacketAdapterType type) {
        switch (type) {
            case CHAT: return new ChatPacketReplacer(bukkitPlugin, replaceMap, replacePAPI);
            case ENTITY: return new EntityPacketReplacer(bukkitPlugin, replaceMap, replacePAPI);
            case BOARD: return new BoardReplacer(bukkitPlugin, replaceMap, replacePAPI);
            case KICK: return new KickReplacer(bukkitPlugin, replaceMap, replacePAPI);
            case OPEN_WINDOW: return new OpenWindowReplacer(bukkitPlugin, replaceMap, replacePAPI);
        }
        return null;
    }
}
