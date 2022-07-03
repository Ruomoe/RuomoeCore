package cc.canyi.core.gui;

import cc.canyi.core.gui.model.RegisteredActiveFunction;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    private final HashMap<GuiEventType, HashSet<RegisteredActiveFunction>> functions;

    public GuiHandler(Inventory inventory) {
        this.handledInv = inventory;
        functions = new HashMap<>();
    }

    public void bind() {
        GuiEventProxy.registerHandler(this);
    }

    public void destroy() {
        GuiEventProxy.unregisterHandler(this);
    }

    public void registerFunction(int slot, GuiEventType type, ActiveFunction function) {
        HashSet<RegisteredActiveFunction> hashSet = functions.containsKey(type) ? functions.get(type) : new HashSet<RegisteredActiveFunction>();
        hashSet.add(new RegisteredActiveFunction(type, slot, function));
        functions.put(type, hashSet);
    }

    public boolean runFunction(GuiEventType type, Inventory inv, Player player, ItemStack cursorItem, ItemStack currentItem, int slot) {
        HashSet<RegisteredActiveFunction> all = functions.get(GuiEventType.ALL);
        if(all != null) {
            for(RegisteredActiveFunction registeredActiveFunction : all) {
                if(registeredActiveFunction.getSlot() == slot)
                    registeredActiveFunction.getFunction().function(inv, player, cursorItem, currentItem);
            }
        }

        HashSet<RegisteredActiveFunction> registeredActiveFunctions = functions.get(type);
        if(registeredActiveFunctions != null) {
            for(RegisteredActiveFunction registeredActiveFunction : registeredActiveFunctions) {
                if(registeredActiveFunction.getSlot() == slot)
                    registeredActiveFunction.getFunction().function(inv, player, cursorItem, currentItem);
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
                default: return false;
            }
        }
        return false;
    }
}
