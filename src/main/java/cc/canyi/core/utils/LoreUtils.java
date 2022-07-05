package cc.canyi.core.utils;

import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.List;
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

    public static List<String> splitListColor(List<String> list) {
        for(int i = 0; i < list.size(); i++) {
            list.set(i, splitColor(list.get(i)));
        }
        return list;
    }

    public static String replaceColorChar(String lore) {
        return lore.replace("&", "§");
    }

    public static List<String> replaceListColorChar(List<String> list) {
        for(int i = 0; i < list.size(); i++) {
            list.set(i, replaceColorChar(list.get(i)));
        }
        return list;
    }


}
