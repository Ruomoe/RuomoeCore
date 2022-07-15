package cc.canyi.core.item;

import cc.canyi.core.item.model.condition.Condition;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemConditionFactory {

    public static ItemCondition createItemCondition(Condition... conditions) {
        return new ItemCondition(Arrays.asList(conditions));
    }

    @Data
    public static class ItemCondition implements Condition{
        private final List<Condition> conditionList;

        @Override
        public boolean check(ItemStack stack) {
            for(Condition condition : conditionList) {
                if(!condition.check(stack)) return false;
            }
            return true;
        }
    }
}
