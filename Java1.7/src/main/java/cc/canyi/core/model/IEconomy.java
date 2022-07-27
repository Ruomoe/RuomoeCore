package cc.canyi.core.model;

import org.bukkit.entity.Player;

public interface IEconomy {
    void takeMoney(Player player, double money);

    void giveMoney(Player player, double money);

    boolean hasMoney(Player player, double money);

    double getBalance(Player player);

    void setMoney(Player player, double money);
}
