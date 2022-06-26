package cc.canyi.core.utils;

import cc.canyi.core.RuomoeCorePlugin;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class ListenerUtils {
    private static final HashMap<Plugin, SavedListeners> registered_listeners = new LinkedHashMap<>();

    @Data
    private static class SavedListeners{
        private final Class<?> mainClass;
        private final Set<Listener> registeredListeners;

        public void registerListener(Listener listener) {
            if(registeredListeners.contains(listener)) RuomoeCorePlugin.getInfoLogger().warning("Already registered " + listener.getClass() + ", the old has been overwrite.");
            registeredListeners.add(listener);
        }

        public void unregisterListener(Listener listener) {
            if(!registeredListeners.contains(listener)) RuomoeCorePlugin.getInfoLogger().warning("Listener " + listener.getClass() + " has never been registered.");
            registeredListeners.remove(listener);
        }
    }

    public static void registerListener(Plugin mainPlugin, Listener listener) {
        SavedListeners savedListeners = registered_listeners.containsKey(mainPlugin) ? registered_listeners.get(mainPlugin) : new SavedListeners(mainPlugin.getClass(), new LinkedHashSet<>());
        savedListeners.registerListener(listener);
        Bukkit.getPluginManager().registerEvents(listener, mainPlugin);
    }

    /**
     * This is useless for bukkit
     */
    @Deprecated
    public static void unregisterListener(Plugin mainPlugin, Listener listener) {
        SavedListeners savedListeners = registered_listeners.containsKey(mainPlugin) ? registered_listeners.get(mainPlugin) : new SavedListeners(mainPlugin.getClass(), new LinkedHashSet<>());
        savedListeners.unregisterListener(listener);
    }

    public static SavedListeners getRegisteredListeners(Plugin mainPlugin) {
        return registered_listeners.get(mainPlugin);
    }

    public static void clear() {
        registered_listeners.clear();
    }
}
