package cc.canyi.core.gui;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.utils.ItemUtils;
import cc.canyi.core.utils.PlayerUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Iterator;
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
            if(RuomoeCorePlugin.isDebug()) {
                System.out.println("SlotType: " + event.getSlotType());
                System.out.println("Slot: " + event.getSlot());
                System.out.println("RowSlot: " + event.getRawSlot());
                System.out.println("Click: " + event.getClick());
            }
            //Not Check Outside
            if((event.getSlot() == -999 && event.getRawSlot() == 999) || event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) {
                return;
            }
            for(GuiHandler handler : handlers) {

                //Not Check Player Inv
                if(handler.isNotCheckPlayerInvSlot() && event.getRawSlot() >= inventory.getSize()) {
                    return;
                }
                boolean cancel = true;
                try{
                    cancel = handler.event(inventory, player, event.getSlot(), event.getClick(), event.getCursor().clone(), event.getCurrentItem().clone());
                }catch (Exception e) {
                    e.printStackTrace();
                }
                if(cancel) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void closeInv(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if(event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();

            Iterator<GuiHandler> handlerIterator = handlers.iterator();
            while(handlerIterator.hasNext()) {
                GuiHandler handler = handlerIterator.next();
                if (handler.getHandledInv().getTitle().equals(inventory.getTitle()) || handler.getHandledInv().equals(inventory)) {
                    for (int slot : handler.getReBackSlots()) {
                        ItemStack stack = inventory.getItem(slot);
                        if(ItemUtils.isItem(stack)) {
                            PlayerUtils.giveItem(player, stack);
                            inventory.setItem(slot, null);
                        }
                    }
//                    handler.destroy();
                    handlerIterator.remove();
                }
            }
        }
    }

    @EventHandler
    public void drag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        for(GuiHandler handler : handlers) {
            if(handler.getHandledInv().getTitle().equals(inventory.getTitle()) || handler.getHandledInv().equals(inventory)) {
                event.setCancelled(handler.isCanceled());
                if(handler.isCanceled() && handler.getCancelDragMessage() != null && event.getWhoClicked() instanceof Player) {
                    ((Player) event.getWhoClicked()).sendMessage(handler.getCancelDragMessage());
                }
            }
        }
    }
}
