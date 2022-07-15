package cc.canyi.core.item.model.condition;

import cc.canyi.core.utils.ItemUtils;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class NameCondition implements Condition{
    private final String name;
    private final boolean equals;
    @Override
    public boolean check(ItemStack stack) {
        if(equals) return ItemUtils.itemDisplay(stack).equals(name);
        else return ItemUtils.itemDisplay(stack).contains(name);
    }
}
