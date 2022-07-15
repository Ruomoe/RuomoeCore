package cc.canyi.core.item.model.condition;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class IDCondition implements Condition{
    private final int id;
    private final short shortId;
    @Override
    public boolean check(ItemStack stack) {
        if(shortId == -1) return stack.getTypeId() == id;
        else if(id == -1) return stack.getDurability() == shortId;

        return stack.getTypeId() == id && stack.getDurability() == shortId;
    }
}
