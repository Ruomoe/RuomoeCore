package cc.canyi.core.I18N;

import cc.canyi.core.I18N.model.Language;
import cc.canyi.core.I18N.model.PluginLanguage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class LanguageManager {
    private static final HashMap<Plugin, PluginLanguage> languageCache = new HashMap<>();

    public static void registerLanguage(Plugin mainPlugin, String name, YamlConfiguration langYaml) {
        PluginLanguage pluginLanguage = languageCache.containsKey(mainPlugin) ? languageCache.get(mainPlugin) : new PluginLanguage(mainPlugin, new HashMap<String, Language>());

        pluginLanguage.getLanguageCache().put(name, new Language(name, langYaml).load());
    }

    public static void registerLanguage(Plugin mainPlugin, PluginLanguage language) {
        languageCache.put(mainPlugin, language);
    }

    public static String transform(Plugin mainPlugin, String str, String name) {
        if(languageCache.containsKey(mainPlugin)) return languageCache.get(mainPlugin).transform(str, name);
        else return mainPlugin.getName() + " Not found any I18N Language for " + str + " | " + name;
    }

    public static String transform(Plugin mainPlugin, String str) {
        if(languageCache.containsKey(mainPlugin)) return languageCache.get(mainPlugin).transform(str);
        else return mainPlugin.getName() + " Not found any I18N Language for " + str;
    }
}
