package cc.canyi.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationUtils {
    public static String loc2Str(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    }
    public static Location str2Loc(String locStr) {
        if(locStr.contains(",")) {
            String[] template = locStr.split(",");
            return new Location(Bukkit.getWorld(template[0]), Double.parseDouble(template[1]), Double.parseDouble(template[2]), Double.parseDouble(template[3]));
        }
        return null;
    }

    public static Location getBlockLocationByPlayer(Player player) {
        return player.getLocation().getBlock().getLocation();
    }
}
