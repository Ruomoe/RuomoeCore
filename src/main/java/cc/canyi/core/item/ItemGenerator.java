package cc.canyi.core.item;

import cc.canyi.core.item.model.ItemByID;
import cc.canyi.core.item.model.ItemByType;
import cc.canyi.core.utils.LoreUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemGenerator {

    @Deprecated
    public static ItemStack generateItemByModel(ItemByID itemByID, int size) {
        ItemStack stack = new ItemStack(Material.getMaterial(itemByID.getId()), size, itemByID.getDur());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(LoreUtils.replaceColorChar(itemByID.getName()));
        meta.setLore(LoreUtils.replaceListColorChar(itemByID.getLore()));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack generateItemByModel(ItemByType itemByType, int size) {
        ItemStack stack = new ItemStack(Material.getMaterial(itemByType.getType()), size, itemByType.getDur());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(LoreUtils.replaceColorChar(itemByType.getName()));
        meta.setLore(LoreUtils.replaceListColorChar(itemByType.getLore()));
        stack.setItemMeta(meta);
        return stack;
    }
}
