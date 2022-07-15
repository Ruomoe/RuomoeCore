package cc.canyi.core.item.model.condition;

import cc.canyi.core.utils.ItemUtils;
import lombok.Data;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Data
public class LoreCondition implements Condition{

    private final String findLore;
    private final boolean equals;

    @Override
    public boolean check(ItemStack stack) {
        if(ItemUtils.isItem(stack)) {
            ItemMeta meta = stack.getItemMeta();
            List<String> lore = meta.getLore();
            if(equals) return lore.contains(findLore);
            else {
                for(String line : lore) {
                    if(line.contains(findLore)) return true;
                }
            }
        }
        return false;
    }
}
