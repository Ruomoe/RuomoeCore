package cc.canyi.core.protocol;

import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;

import java.util.Map;

public class TitleReplacer extends BasePacketAdapter{
    public TitleReplacer(BukkitPlugin plugin, Map<Object, Object> repMap, boolean isPAPI) {
        super(plugin, repMap, isPAPI, PacketType.Play.Server.TITLE);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        String replaced;
        Player user = event.getPlayer();
        if (user == null)
            return;
        PacketContainer packet = event.getPacket();
        StructureModifier<WrappedChatComponent> wrappedChatComponentStructureModifier = packet.getChatComponents();
        WrappedChatComponent wrappedChatComponent = wrappedChatComponentStructureModifier.read(0);
        if (wrappedChatComponent != null) {
            String json = wrappedChatComponent.getJson();
            replaced = replace(user, json);
        } else {
            replaced = processSpigotComponent(packet.getModifier(), event, user);
//            if (replaced == null)
//                replaced = processPaperComponent(packet.getModifier(), packetEvent, user);
        }
        if (replaced != null)
            wrappedChatComponentStructureModifier.write(0, WrappedChatComponent.fromJson(replaced));
    }

    protected String processSpigotComponent(StructureModifier<Object> modifier, PacketEvent packetEvent, Player user) {
        StructureModifier<BaseComponent[]> componentModifier = modifier.withType(BaseComponent[].class);
        if (componentModifier.size() == 0)
            return null;
        BaseComponent[] read = componentModifier.read(0);
        if (read == null)
            return null;
        String result = replace(user, ComponentSerializer.toString(read));
//                getReplacedJson(packetEvent, user, this.listenType, ComponentSerializer.toString(read), this.filter);
        componentModifier.write(0, null);
        return (result == null) ? "{\"text\":\"TITLE_REPLACER blocked message. If you see this, it's caused by other plugin(s).\"}" : result;
    }

//    protected String processPaperComponent(StructureModifier<Object> modifier, PacketEvent packetEvent, Player user) {
//        StructureModifier<Component> componentModifier = modifier.withType(Component.class);
//        Component read = componentModifier.read(0);
//        if (read == null)
//            return null;
//        String result = getReplacedJson(packetEvent, user, this.listenType, (String)PaperUtils.getPaperGsonComponentSerializer().serialize(read), this.filter);
//        componentModifier.write(0, null);
//        return (result == null) ? "{\"text\":\"TITLE_REPLACER blocked message. If you see this, it's caused by other plugin(s).\"}" : result;
//    }
}
