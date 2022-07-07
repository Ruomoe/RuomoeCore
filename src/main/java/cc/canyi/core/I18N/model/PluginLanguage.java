package cc.canyi.core.I18N.model;

import lombok.Data;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

@Data
public class PluginLanguage {
    private final Plugin plugin;

    private final HashMap<String, Language> languageCache;

    public String transform(String str, String name) {
        if(languageCache.containsKey(name)) return languageCache.get(name).transform(str);
        else if(getDefault() != null) return getDefault().transform(str);
        else return plugin.getName() + " Not found any I18N Language for " + str + " | " + name;
    }
    public String transform(String str) {
        if(getDefault() != null) return getDefault().transform(str);
        else return plugin.getName() + " Not found any I18N Language for " + str;
    }

    public Language getDefault() {
        if(languageCache.containsKey("zh_CN")) return languageCache.get("zh_CN");
        else return languageCache.values().stream().findFirst().orElse(null);
    }
}
