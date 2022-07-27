package cc.canyi.core.papi.model;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface IPAPIAgent {
    String callback(Player player, String params);
}
