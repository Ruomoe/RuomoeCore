package cc.canyi.core.plugin;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public abstract class BukkitPlugin extends JavaPlugin {

    @Getter
    private static String root;

    @Getter
    private static BukkitPlugin instance;

    @Getter
    private static final HashMap<String, Boolean> releaseFileNames = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;
        root = this.getDataFolder().getAbsolutePath();

        this.getLogger().info(getClass().getSimpleName() + " has been loaded!");
    }

    @Override
    public void onEnable() {
        this.getLogger().info(getClass().getSimpleName() + " has been enable!");
    }

    public void createDefaultConfig() {
        this.saveDefaultConfig();
    }

    public void registerReleaseFile(String fileName, boolean replaced) {
        releaseFileNames.put(fileName, replaced);
    }

    public void releaseFile() {
        for(String fileName : releaseFileNames.keySet()) {
            this.saveResource(fileName, releaseFileNames.get(fileName));
        }
    }


}
