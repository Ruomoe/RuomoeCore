package cc.canyi.core;

import cc.canyi.core.command.CommandManager;
import cc.canyi.core.gui.GuiEventProxy;
import cc.canyi.core.plugin.BukkitPlugin;
import cc.canyi.core.utils.ListenerUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;

public class RuomoeCorePlugin extends BukkitPlugin {

    @Getter
    @Setter
    private static boolean debug;

    @Getter
    @Setter
    private static Logger infoLogger;

    @Override
    public void onEnable() {
        infoLogger = this.getLogger();

        createDefaultConfig();

        debug = this.getConfig().getBoolean("DebugMode");
        CommandManager.clear();
        ListenerUtils.clear();

        ListenerUtils.registerListener(this, new GuiEventProxy());
        this.getLogger().info("RuomoeCore enable.");
    }
}
