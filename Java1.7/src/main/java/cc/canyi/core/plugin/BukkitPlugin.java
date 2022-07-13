package cc.canyi.core.plugin;

import cc.canyi.core.RuomoeCorePlugin;
import cn.hutool.core.io.IoUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

public abstract class BukkitPlugin extends JavaPlugin {

    @Getter
    private final HashMap<String, Boolean> releaseFileNames = new HashMap<>();

    @Override
    public void onLoad() {
        this.getLogger().info(getClass().getSimpleName() + " has been loaded!");
    }

    public String getRootString(){
        return this.getDataFolder().getAbsolutePath();
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

    @SneakyThrows
    public void createDefaultHuToolMySQLSetting(String path) {
        File file = new File(path);
        if(!file.exists())
            IoUtil.copy(RuomoeCorePlugin.class.getClassLoader().getResourceAsStream("db.setting"), new FileOutputStream(file));
    }
}
