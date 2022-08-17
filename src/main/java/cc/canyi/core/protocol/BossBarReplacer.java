package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.util.Map;

public class BossBarReplacer extends BasePacketAdapter{
    public BossBarReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.BOSS);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player user = event.getPlayer();
        if (user == null)
            return;
        StructureModifier<WrappedChatComponent> wrappedChatComponentStructureModifier = event.getPacket().getChatComponents();
        if (wrappedChatComponentStructureModifier.size() != 0) {
            WrappedChatComponent wrappedChatComponent = wrappedChatComponentStructureModifier.read(0);
            String json = wrappedChatComponent.getJson();
            WrappedChatComponent replaced = WrappedChatComponent.fromJson(replace(user, json));
            wrappedChatComponentStructureModifier.write(0, replaced);
        }
    }
}
