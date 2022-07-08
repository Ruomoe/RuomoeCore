package cc.canyi.core.I18N.model;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.utils.LoreUtils;
import lombok.Data;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

@Data
public class Language {
    private final String name;

    private final YamlConfiguration langYaml;

    private HashMap<String, String> langCacheMap;

    public String transform(String key) {
        return langCacheMap.containsKey(key) ? langCacheMap.get(key) : name + " Not found " + key;
    }

    public Language load() {
        langCacheMap = new HashMap<>();
        for(String key : langYaml.getKeys(false)) {
            if(RuomoeCorePlugin.isDebug()) {
                RuomoeCorePlugin.getInfoLogger().info(name + " loaded " + key);
            }
            langCacheMap.put(key, LoreUtils.replaceColorChar(langYaml.getString(key)));
        }
        return this;
    }
}
