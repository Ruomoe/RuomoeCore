package cc.canyi.core.gui;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class GuiEventProxy implements Listener {
    @Getter
    private static final HashSet<GuiHandler> handlers = new LinkedHashSet<>();

    public static void registerHandler(GuiHandler handler) {
        handlers.add(handler);
    }

    public static void unregisterHandler(GuiHandler handler) {
        handlers.remove(handler);
    }

    @EventHandler(ignoreCancelled = true)
    public void clickInv(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            for(GuiHandler handler : handlers) {
                boolean cancel = handler.event(inventory, player, event.getSlot(), event.getClick(), event.getCursor().clone(), event.getCurrentItem().clone());
                if(cancel) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void closeInv(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        for(GuiHandler handler : handlers) {
            if(handler.getHandledInv().getTitle().equals(inventory.getTitle()) || handler.getHandledInv().equals(inventory)) {
                handler.destroy();
            }
        }
    }
}
