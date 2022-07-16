package cc.canyi.core.gui;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.gui.model.RegisteredActiveFunction;
import cc.canyi.core.task.TaskHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

@Getter
public class GuiHandler {
    private final Inventory handledInv;

    @Setter
    private boolean canceled;

    @Setter
    private boolean notCheckPlayerInvSlot;

    @Setter
    private String cancelDragMessage;

    private final HashMap<GuiEventType, HashSet<RegisteredActiveFunction>> functions;

    private final HashSet<Integer> reBackSlots;

    private final HashSet<TaskHandler> updateTasks;

    private final HashSet<Integer> allowDragSlots;

    public GuiHandler(Inventory inventory) {
        this.handledInv = inventory;
        functions = new HashMap<>();
        reBackSlots = new HashSet<>();
        updateTasks = new HashSet<>();
        allowDragSlots = new HashSet<>();
    }

    public void bind() {GuiEventProxy.registerHandler(this);}

    public void destroy() {
        GuiEventProxy.unregisterHandler(this);
    }

    public void registerFunction(int slot, GuiEventType type, ActiveFunction function) {
        HashSet<RegisteredActiveFunction> hashSet = functions.containsKey(type) ? functions.get(type) : new HashSet<RegisteredActiveFunction>();
        hashSet.add(new RegisteredActiveFunction(type, slot, function));
        functions.put(type, hashSet);

        if(RuomoeCorePlugin.isDebug()) {
            System.out.println("Gui: " + handledInv.getTitle() +  " Now Functions " + function);
        }
    }

    public void registerReBackItemSlot(int slot) {
        reBackSlots.add(slot);
    }

    public void registerAllowDragSlot(int slot) {
        allowDragSlots.add(slot);
    }

    public boolean runFunction(GuiEventType type, Inventory inv, Player player, ItemStack cursorItem, ItemStack currentItem, int slot) {
        boolean canceled = this.canceled;
        if(RuomoeCorePlugin.isDebug()) {
            System.out.println("Gui: " + inv.getTitle() + " TryRun " + type);
        }
        HashSet<RegisteredActiveFunction> all = functions.get(GuiEventType.ALL);
        if(all != null) {
            for(RegisteredActiveFunction registeredActiveFunction : all) {
                if(registeredActiveFunction.getSlot() == slot) {
                    return registeredActiveFunction.getFunction().function(inv, player, cursorItem, currentItem);
                }
            }
        }

        HashSet<RegisteredActiveFunction> registeredActiveFunctions = functions.get(type);
        if(registeredActiveFunctions != null) {
            for(RegisteredActiveFunction registeredActiveFunction : registeredActiveFunctions) {
                if(registeredActiveFunction.getSlot() == slot) {
                    return registeredActiveFunction.getFunction().function(inv, player, cursorItem, currentItem);
                }
            }
        }

        return canceled;
    }

    public boolean event(Inventory inv, Player player, int slot, ClickType clickType, ItemStack cursorItem, ItemStack currentItem) {
        if(inv.getTitle().equals(handledInv.getTitle()) || inv.equals(handledInv)) {
            //is this handledInv
            switch (clickType) {
                case LEFT: {
                    return runFunction(GuiEventType.LEFT_CLICK, inv, player, cursorItem, currentItem, slot);
                }
                case RIGHT: {
                    return runFunction(GuiEventType.RIGHT_CLICK, inv, player, cursorItem, currentItem, slot);
                }
                case SHIFT_LEFT: {
                    return runFunction(GuiEventType.SHIFT_LEFT_CLICK, inv, player, cursorItem, currentItem, slot);
                }
                case SHIFT_RIGHT: {
                    return runFunction(GuiEventType.SHIFT_RIGHT_CLICK, inv, player, cursorItem, currentItem, slot);
                }
                default: return canceled;
            }
        }
        return false;
    }
}
