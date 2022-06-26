package cc.canyi.core.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FileUtils {
    public static File createDirIfNotExist(JavaPlugin plugin, String dirName) {
        String root = plugin.getDataFolder().getAbsolutePath();
        File rootDir = new File(root);
        if(!rootDir.exists() || !rootDir.isDirectory()) rootDir.mkdirs();
        File targetDir = new File(rootDir, dirName);
        if(!targetDir.exists() || !targetDir.isDirectory()) targetDir.mkdirs();
        return targetDir;
    }

    public static YamlConfiguration getYamlByFileName(JavaPlugin plugin, String name) {
        String root = plugin.getDataFolder().getAbsolutePath();
        File rootDir = new File(root);
        if(rootDir.exists()) {
            File targetYamlFile = new File(rootDir, name);
            if(targetYamlFile.exists()) return YamlConfiguration.loadConfiguration(targetYamlFile);
            else return null;
        }
        return null;
    }

    public static boolean isExists(String name) {
        return new File(name).exists();
    }

    public static boolean isDir(String name) {
        return new File(name).isDirectory();
    }
}
