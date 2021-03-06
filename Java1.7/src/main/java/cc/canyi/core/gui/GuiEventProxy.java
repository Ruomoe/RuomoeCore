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

import java.util.*;

public class GuiEventProxy implements Listener {
    @Getter
    private static final HashSet<GuiHandler> handlers = new LinkedHashSet<>();

    public static void registerHandler(GuiHandler handler) {
        handlers.add(handler);
    }

    public static void unregisterHandler(GuiHandler handler) {
        handlers.remove(handler);
    }

    private final HashMap<Player, Long> clickTimeMap = new LinkedHashMap<>();

    private final HashSet<Player> foreachHandlerPlayers = new LinkedHashSet<>();

    @EventHandler(ignoreCancelled = true)
    public void clickInv(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            if (RuomoeCorePlugin.isDebug()) {
                System.out.println("SlotType: " + event.getSlotType());
                System.out.println("Slot: " + event.getSlot());
                System.out.println("RowSlot: " + event.getRawSlot());
                System.out.println("Click: " + event.getClick());
            }
            //Not Check Outside
            if ((event.getSlot() == -999 && event.getRawSlot() == 999) || event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) {
                return;
            }

            //防止多重点击 保证逻辑执行完毕
            if (foreachHandlerPlayers.contains(player)) {
                if(RuomoeCorePlugin.isDebug()) System.out.println("Foreach-ing return.");
                event.setCancelled(true);
                return;
            }
            List<GuiHandler> filterGuiHandlers = filterGuiHandlerByTitle(inventory);
            if (filterGuiHandlers.isEmpty()){
                if(RuomoeCorePlugin.isDebug()) System.out.println("Empty GuiHandler return.");
                return;
            }

            long time = clickTimeMap.containsKey(player) ? clickTimeMap.get(player) : System.currentTimeMillis() - 1000;
            if (System.currentTimeMillis() - time < 500) {
                //节流
                event.setCancelled(true);
                if(RuomoeCorePlugin.isDebug()) System.out.println("ClickTime < 500ms return " + System.currentTimeMillis() + " | " + time);
                return;
            }

            clickTimeMap.put(player, System.currentTimeMillis());


            foreachHandlerPlayers.add(player);

            for (GuiHandler handler : filterGuiHandlers) {

                //Not Check Player Inv
                if (handler.isNotCheckPlayerInvSlot() && event.getRawSlot() >= inventory.getSize()) {
                    foreachHandlerPlayers.remove(player);
                    return;
                }
                //数字键
                if (event.getClick().equals(ClickType.NUMBER_KEY)) {
                    event.setCancelled(handler.isCanceled());
                    foreachHandlerPlayers.remove(player);
                    return;
                }
                boolean cancel = true;
                try {
                    cancel = handler.event(inventory, player, event.getSlot(), event.getClick(), event.getCursor().clone(), event.getCurrentItem().clone());
                } catch (Exception e) {
                    e.printStackTrace();
                    foreachHandlerPlayers.remove(player);
                }
                if (cancel) event.setCancelled(true);
            }

            foreachHandlerPlayers.remove(player);
        }
    }

    public List<GuiHandler> filterGuiHandlerByTitle(Inventory clickInv) {
        List<GuiHandler> filters = new ArrayList<>();
        for (GuiHandler handler : handlers) {
            if (handler.getHandledInv().getTitle().equals(clickInv.getTitle())) filters.add(handler);
        }
        return filters;
    }

    @EventHandler
    public void openGui(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            if (destroyPlayers.contains(player)) {
                event.setCancelled(true);
            }
        }
    }

    @Getter
    private static final HashSet<Player> destroyPlayers = new LinkedHashSet<>();

    @EventHandler
    public void closeInv(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();

            Iterator<GuiHandler> handlerIterator = handlers.iterator();
            while (handlerIterator.hasNext()) {
                GuiHandler handler = handlerIterator.next();
                if (handler.getHandledInv().getTitle().equals(inventory.getTitle()) || handler.getHandledInv().equals(inventory)) {
                    destroyPlayers.add(player);
                    for (int slot : handler.getReBackSlots()) {
                        ItemStack stack = inventory.getItem(slot);
                        if (ItemUtils.isItem(stack)) {
                            PlayerUtils.giveItem(player, stack);
                            inventory.setItem(slot, null);
                        }
                    }
                    if (ItemUtils.isItem(player.getItemOnCursor())) {
                        PlayerUtils.giveItem(player, player.getItemOnCursor());
                        player.setItemOnCursor(null);
                    }
                    //取消刷新事件
                    for (TaskHandler taskHandler : handler.getUpdateTasks()) taskHandler.cancel();
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
        for (GuiHandler handler : handlers) {
            if (handler.getHandledInv().getTitle().equals(inventory.getTitle()) || handler.getHandledInv().equals(inventory)) {
                if (handler.getAllowDragSlots().isEmpty()) {
                    event.setCancelled(handler.isCanceled());
                    if (handler.isCanceled() && handler.getCancelDragMessage() != null && event.getWhoClicked() instanceof Player) {
                        ((Player) event.getWhoClicked()).sendMessage(handler.getCancelDragMessage());
                    }
                } else {
                    //允许拖拽
                    if (event.getInventorySlots().size() > 1) {
                        event.setCancelled(event.getInventorySlots().containsAll(handler.getAllowDragSlots()) && handler.getAllowDragSlots().contains(event.getInventorySlots()));
                    } else if (event.getInventorySlots().size() == 1) {
                        event.setCancelled(handler.getAllowDragSlots().containsAll(event.getInventorySlots()));
                    } else {
                        event.setCancelled(handler.isCanceled());
                    }
                }
            }
        }
    }
}
