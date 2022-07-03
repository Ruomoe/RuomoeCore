package cc.canyi.core.utils;

import cc.canyi.core.gui.GuiHandler;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GuiUtils {
    public static Inventory createInventory(String title, int size) {
        return Bukkit.createInventory(null, size, title);
    }

    public static GuiHandler createGuiHandler(Inventory inventory) {
        return new GuiHandler(inventory);
    }

    public static GuiHandler createActiveGuiHandler(Inventory inventory) {
        GuiHandler handler = createGuiHandler(inventory);
        handler.bind();
        return handler;
    }
}
