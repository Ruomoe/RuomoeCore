package cc.canyi.core.utils;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.exception.RuomoeCoreInvokeException;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityUtils {
    public static List<ItemStack> getEquipments(LivingEntity entity) {
        return new ArrayList<>(Arrays.asList(entity.getEquipment().getArmorContents()));
    }

    public static void setPlayerEquipments(LivingEntity entity, List<ItemStack> equipments) {
        if(equipments.size() != 4) {
            RuomoeCorePlugin.getInfoLogger().warning("You usage a small than 4 list to replace player equipment!!!");
            throw new RuomoeCoreInvokeException("You usage a small than 4 list to replace player equipment");
        }
        EntityEquipment playerInventory = entity.getEquipment();
        if(equipments.get(0) != null)
            playerInventory.setHelmet(equipments.get(0));
        if(equipments.get(1) != null)
            playerInventory.setChestplate(equipments.get(1));
        if(equipments.get(2) != null)
            playerInventory.setLeggings(equipments.get(2));
        if(equipments.get(3) != null)
            playerInventory.setBoots(equipments.get(3));
    }
}
