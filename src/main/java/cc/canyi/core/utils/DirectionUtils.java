package cc.canyi.core.utils;

import org.bukkit.Location;

public class DirectionUtils {

    public static double getDistance(Location loc1, Location loc2) {
        if(loc1 == null || loc2 == null) return Double.MAX_VALUE;
        if(!loc1.getWorld().getName().equals(loc2.getWorld().getName())) return Double.MAX_VALUE;
        return loc1.distance(loc2);
    }
    
}
