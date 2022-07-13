package cc.canyi.core.utils;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.exception.RuomoeCoreInvokeException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    public static void runCommandByConsole(Player player, String command, String replace2PlayerNameStr) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace(replace2PlayerNameStr, player.getName()));
    }

    public static void runCommandByConsole(Player player, List<String> commands, String replace2PlayerNameStr) {
        for(String command : commands) runCommandByConsole(player, command, replace2PlayerNameStr);
    }

    public static void runCommandByConsole(Player player, String replace2PlayerNameStr, String... commands) {
        for(String command : commands) runCommandByConsole(player, command, replace2PlayerNameStr);
    }

    public static void runCommandByOp(Player player, String command, String replace2PlayerNameStr) {
        boolean isOp = player.isOp();
        player.setOp(true);
        player.chat("/" + command.replace(replace2PlayerNameStr, player.getName()));
        player.setOp(isOp);
    }

    public static void runCommandByOp(Player player, List<String> commands, String replace2PlayerNameStr) {
        boolean isOp = player.isOp();
        player.setOp(true);
        for(String command : commands) {
            player.chat("/" + command.replace(replace2PlayerNameStr, player.getName()));
        }
        player.setOp(isOp);
    }

    public static void runCommandByOp(Player player,  String replace2PlayerNameStr, String... commands) {
        boolean isOp = player.isOp();
        player.setOp(true);
        for(String command : commands) {
            player.chat("/" + command.replace(replace2PlayerNameStr, player.getName()));
        }
        player.setOp(isOp);
    }

    public static void giveItem(Player player, ItemStack stack) {
        if(ItemUtils.checkInvHasEmptySlot(player)) player.getInventory().addItem(stack);
        else player.getWorld().dropItem(player.getLocation(), stack);
    }

    public static void giveItem(Player player, List<ItemStack> stacks) {
        if(ItemUtils.getPlayerInvEmptySlot(player) >= stacks.size()) {
            for(ItemStack stack : stacks) player.getInventory().addItem(stack);
        }else {
            for(ItemStack stack : stacks) player.getWorld().dropItem(player.getLocation(), stack);
        }
    }

    public static List<ItemStack> getEquipments(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        List<ItemStack> stacks = new ArrayList<>();

        stacks.add(ItemUtils.isItem(playerInventory.getHelmet()) ? playerInventory.getHelmet().clone() : null);
        stacks.add(ItemUtils.isItem(playerInventory.getChestplate()) ? playerInventory.getChestplate().clone() : null);
        stacks.add(ItemUtils.isItem(playerInventory.getLeggings()) ? playerInventory.getLeggings().clone() : null);
        stacks.add(ItemUtils.isItem(playerInventory.getBoots()) ? playerInventory.getBoots().clone() : null);

        return stacks;
    }

    public static List<ItemStack> getEquipmentsAndHand(Player player) {
        List<ItemStack> stacks = getEquipments(player);

        stacks.add(ItemUtils.isItem(player.getItemInHand()) ? player.getItemInHand().clone() : null);

        return stacks;
    }

    public static void setPlayerEquipments(Player player, List<ItemStack> equipments) {
        if(equipments.size() != 4) {
            RuomoeCorePlugin.getInfoLogger().warning("You usage a small than 4 list to replace player equipment!!!");
            throw new RuomoeCoreInvokeException("You usage a small than 4 list to replace player equipment");
        }
        PlayerInventory playerInventory = player.getInventory();
        if(equipments.get(0) != null)
            playerInventory.setHelmet(equipments.get(0));
        if(equipments.get(1) != null)
            playerInventory.setChestplate(equipments.get(1));
        if(equipments.get(2) != null)
            playerInventory.setLeggings(equipments.get(2));
        if(equipments.get(3) != null)
            playerInventory.setBoots(equipments.get(3));
    }

    public static Player getPlayerIfOnline(String playerName) {
        if(Bukkit.getPlayer(playerName) != null && Bukkit.getPlayer(playerName).isOnline()) return Bukkit.getPlayer(playerName);
        return null;
    }
}
