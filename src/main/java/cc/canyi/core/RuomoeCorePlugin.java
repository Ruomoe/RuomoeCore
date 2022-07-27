package cc.canyi.core;

import cc.canyi.core.command.CommandManager;
import cc.canyi.core.gui.GuiEventProxy;
import cc.canyi.core.model.IEconomy;
import cc.canyi.core.model.ProtocolManager;
import cc.canyi.core.model.VaultEconomy;
import cc.canyi.core.plugin.BukkitPlugin;
import cc.canyi.core.utils.ListenerUtils;
import cc.canyi.core.utils.PAPIUtils;
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

    @Getter
    private static IEconomy defaultEconomy;

    @Override
    public void onEnable() {
        infoLogger = this.getLogger();

        createDefaultConfig();

        debug = this.getConfig().getBoolean("DebugMode");
        CommandManager.clear();
        ListenerUtils.clear();

        ListenerUtils.registerListener(this, new GuiEventProxy());

        if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            protocolManager = new ProtocolManager();
            protocolManager.init();
            this.getLogger().info("ProtocolLib is ready.");
        }

        if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
            VaultEconomy vaultEconomy = new VaultEconomy();
            vaultEconomy.init();
            defaultEconomy = vaultEconomy;
            this.getLogger().info("Vault is ready.");
        }

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PAPIUtils.setPapiLoaded(true);
            this.getLogger().info("PlaceholderAPI is ready.");
        }


        this.getLogger().info("RuomoeCore enable.");
    }

}
