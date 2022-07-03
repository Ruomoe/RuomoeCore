package cc.canyi.core.gui.model;

import cc.canyi.core.gui.ActiveFunction;
import cc.canyi.core.gui.GuiEventType;
import lombok.Data;

@Data
public class RegisteredActiveFunction {
    private final GuiEventType type;

    private final int slot;

    private final ActiveFunction function;
}
