package cc.canyi.core.utils;

import cc.canyi.core.plugin.BukkitPlugin;
import cc.canyi.core.protocol.BasePacketAdapter;
import cc.canyi.core.protocol.ChatPacketReplacer;
import cc.canyi.core.protocol.EntityPacketReplacer;
import cc.canyi.core.protocol.EnumPacketAdapterType;

import java.util.Map;

public class ProtocolUtils {
    public static BasePacketAdapter createPacketAdapter(BukkitPlugin bukkitPlugin, Map<Object, Object> replaceMap, boolean replacePAPI, EnumPacketAdapterType type) {
        switch (type) {
            case CHAT: return new ChatPacketReplacer(bukkitPlugin, replaceMap, replacePAPI);
            case ENTITY: return new EntityPacketReplacer(bukkitPlugin, replaceMap, replacePAPI);
        }
        return null;
    }
}
