package cc.canyi.core.gui;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.task.TaskHandler;
import cc.canyi.core.utils.ItemUtils;
import cc.canyi.core.utils.PlayerUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
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
                //数字键
                if(event.getClick().equals(ClickType.NUMBER_KEY)){
                    event.setCancelled(handler.isCanceled());
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
    public void openGui(InventoryOpenEvent event) {
        if(event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            if(destroyPlayers.contains(player)) {
                event.setCancelled(true);
            }
        }
    }

    @Getter
    private static final HashSet<Player> destroyPlayers = new LinkedHashSet<>();

    @EventHandler
    public void closeInv(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if(event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            Iterator<GuiHandler> handlerIterator = handlers.iterator();
            while(handlerIterator.hasNext()) {
                GuiHandler handler = handlerIterator.next();
                if (handler.getHandledInv().getTitle().equals(inventory.getTitle()) || handler.getHandledInv().equals(inventory)) {
                    destroyPlayers.add(player);
                    handler.getReBackSlots().forEach(slot -> {
                        ItemStack stack = inventory.getItem(slot);
                        if(ItemUtils.isItem(stack)) {
                            PlayerUtils.giveItem(player, stack);
                            inventory.setItem(slot, null);
                        }
                    });
                    if(ItemUtils.isItem(player.getItemOnCursor())) {
                        PlayerUtils.giveItem(player, player.getItemOnCursor());
                        player.setItemOnCursor(null);
                    }
                    //取消刷新事件
                    handler.getUpdateTasks().forEach(TaskHandler::cancel);
//                    handler.destroy();
                    handlerIterator.remove();
                    destroyPlayers.remove(player);

                    //优化效率
                    break;
                }
            }

        }
    }

    @EventHandler
    public void drag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        for(GuiHandler handler : handlers) {
            if(handler.getHandledInv().getTitle().equals(inventory.getTitle()) || handler.getHandledInv().equals(inventory)) {
                if(handler.getAllowDragSlots().isEmpty()) {
                    event.setCancelled(handler.isCanceled());
                    if (handler.isCanceled() && handler.getCancelDragMessage() != null && event.getWhoClicked() instanceof Player) {
                        ((Player) event.getWhoClicked()).sendMessage(handler.getCancelDragMessage());
                    }
                }else {
                    //允许拖拽
                    if (event.getInventorySlots().size() > 1) {
                        event.setCancelled(event.getInventorySlots().containsAll(handler.getAllowDragSlots()) && handler.getAllowDragSlots().contains(event.getInventorySlots()));
                    } else if(event.getInventorySlots().size() == 1) {
                        event.setCancelled(handler.getAllowDragSlots().containsAll(event.getInventorySlots()));
                    }else {
                        event.setCancelled(handler.isCanceled());
                    }
                }
            }
        }
    }
}
