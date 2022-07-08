package cc.canyi.core.nms;

import cc.canyi.core.nms.v1_12_R1.NMS1_12_R1;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class NMS {
    private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

    public static void sendShowItemMessage(Player handler, String message, String replaced, ItemStack stack) {
        if (version.equals("v1_12_R1")) {
            NMS1_12_R1.sendShowItemMessage(handler, message, replaced, stack);
        }
    }

    public static void sendShowItemMessage(List<Player> players, String message, String replaced, ItemStack stack) {
        if (version.equals("v1_12_R1")) {
            NMS1_12_R1.sendShowItemMessage(players, message, replaced, stack);
        }
    }

}
