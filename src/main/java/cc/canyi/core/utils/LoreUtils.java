package cc.canyi.core.utils;

import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoreUtils {
    @Getter
    private static final Pattern NUMBER = Pattern.compile("\\d+(\\.\\d)?");

    public static double getDHas(String lore){
        Matcher matcher = NUMBER.matcher(ChatColor.stripColor(lore));
        if(matcher.find()){
            return Double.parseDouble(matcher.group());
        }else{
            return 0.0D;
        }
    }
    public static float getFDHas(String lore){
        return (float) getDHas(lore);
    }
    public static int getIHas(String lore){
        return (int) getDHas(lore);
    }

    public static String splitColor(String line) {
        return ChatColor.stripColor(line);
    }

}
