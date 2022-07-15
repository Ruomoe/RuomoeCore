package cc.canyi.core.item.model.condition;

import org.bukkit.inventory.ItemStack;

public interface Condition {
    boolean check(ItemStack stack);
}
