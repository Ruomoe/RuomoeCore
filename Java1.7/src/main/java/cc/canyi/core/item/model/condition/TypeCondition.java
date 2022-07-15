package cc.canyi.core.item.model.condition;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class TypeCondition implements Condition{
    private final String type;
    @Override
    public boolean check(ItemStack stack) {
        return stack == null ? type.equalsIgnoreCase("AIR") : stack.getType().name().equalsIgnoreCase(type);
    }
}
