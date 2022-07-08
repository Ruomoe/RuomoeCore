package cc.canyi.core;

import cc.canyi.core.command.CommandManager;
import cc.canyi.core.gui.GuiEventProxy;
import cc.canyi.core.plugin.BukkitPlugin;
import cc.canyi.core.utils.ListenerUtils;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class RuomoeCorePlugin extends BukkitPlugin {

    @Getter
    @Setter
    private static boolean debug;

    @Getter
    @Setter
    private static Logger infoLogger;

    @Getter
    private static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        infoLogger = this.getLogger();

        createDefaultConfig();

        debug = this.getConfig().getBoolean("DebugMode");
        CommandManager.clear();
        ListenerUtils.clear();

        ListenerUtils.registerListener(this, new GuiEventProxy());
        if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            protocolManager = ProtocolLibrary.getProtocolManager();
            this.getLogger().info("ProtocolLib is ready.");
        }
        this.getLogger().info("RuomoeCore enable.");
    }
}
