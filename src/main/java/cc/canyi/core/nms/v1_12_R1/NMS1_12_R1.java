package cc.canyi.core.nms.v1_12_R1;

import cc.canyi.core.nms.model.ShowItem;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class NMS1_12_R1 {
    public static void sendActionBar(Player player, String line) {

        //获取player对应的entityPlayer

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        //创建并发送数据包

        entityPlayer.playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(line), ChatMessageType.GAME_INFO));
    }

    public static void sendChatMessage(Player player, BaseComponent baseComponent) {
        player.spigot().sendMessage(baseComponent);
    }

    public static void sendChatMessage(Player player, BaseComponent... baseComponents) {
        player.spigot().sendMessage(baseComponents);
    }

    public static void sendShowItemMessage(Player player, String message, String replaced, ItemStack stack) {
        String[] part = message.split(replaced);
        TextComponent start = new TextComponent(part[0]);
        TextComponent end = new TextComponent(part[1]);
        TextComponent item = new TextComponent(replaced);
        item.setHoverEvent(new ShowItem(stack).hoverEvent());

        sendChatMessage(player, start, item, end);
    }

    public static void sendShowItemMessage(List<Player> players, String message, String replaced, ItemStack stack) {
        players.forEach(player -> sendShowItemMessage(player, message, replaced, stack));
    }

}
