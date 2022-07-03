package cc.canyi.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface ActiveFunction {
    public abstract void function(Inventory inv, Player player, ItemStack cursorItem, ItemStack currentItem);
}
