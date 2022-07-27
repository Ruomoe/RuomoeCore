package cc.canyi.core.model;

import cc.canyi.core.RuomoeCorePlugin;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomy implements IEconomy{

    @Getter
    private Economy economy;

    public void init() {
        if(!setupEconomy()) {
            RuomoeCorePlugin.getInfoLogger().warning("Vault Economy init error!");
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    @Override
    public void takeMoney(Player player, double money) {
        if(economy == null) {
            RuomoeCorePlugin.getInfoLogger().warning("Vault Economy init error!");
            return;
        }
        economy.withdrawPlayer(player, money);
    }

    @Override
    public void giveMoney(Player player, double money) {
        if(economy == null) {
            RuomoeCorePlugin.getInfoLogger().warning("Vault Economy init error!");
            return;
        }

        economy.depositPlayer(player, money);
    }

    @Override
    public boolean hasMoney(Player player, double money) {
        if(economy == null) {
            RuomoeCorePlugin.getInfoLogger().warning("Vault Economy init error!");
            return false;
        }
        return economy.has(player, money);
    }

    @Override
    public double getBalance(Player player) {
        if(economy == null) {
            RuomoeCorePlugin.getInfoLogger().warning("Vault Economy init error!");
            return 0;
        }
        return economy.getBalance(player);
    }

    @Override
    public void setMoney(Player player, double money) {
        if(economy == null) {
            RuomoeCorePlugin.getInfoLogger().warning("Vault Economy init error!");
            return;
        }
        economy.withdrawPlayer(player, getBalance(player));
        economy.depositPlayer(player, money);
    }
}
