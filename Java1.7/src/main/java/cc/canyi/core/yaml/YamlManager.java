package cc.canyi.core.yaml;

import cc.canyi.core.utils.FileUtils;
import cc.canyi.core.yaml.model.YamlData;
import cc.canyi.core.yaml.model.YamlDataTable;
import cn.hutool.core.util.CharUtil;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YamlManager {

    public static YamlDataTable loadTableFromDir(JavaPlugin plugin, String dirName) {
        File dir = FileUtils.createDirIfNotExist(plugin, dirName);
        return loadTableFromDir(dir);
    }

    public static YamlDataTable loadTableFromDir(File dir) {
        File[] listFiles = dir.listFiles();
        if(listFiles != null) {
            List<YamlData> yamlDataList = new ArrayList<>();
            for(File file : listFiles) {
                if(file.isFile() && !file.isDirectory())
                    yamlDataList.add(new YamlData(mainName(file.getName()), file, YamlConfiguration.loadConfiguration(file)));
            }
            return new YamlDataTable(dir, yamlDataList);
        }
        return new YamlDataTable(dir, new ArrayList<YamlData>());
    }

    public static YamlData getYamlDataFromFile(File file) {
        return new YamlData(mainName(file.getName()), file, YamlConfiguration.loadConfiguration(file));
    }

    public static YamlData findDataFromDir(File dir, String name) {
        File yamlFile = new File(dir, name + ".yml");
        if(yamlFile.exists() && ! yamlFile.isDirectory()) return new YamlData(mainName(yamlFile.getName()), yamlFile, YamlConfiguration.loadConfiguration(yamlFile));
        return null;
    }

    public static YamlData findDataFromDir(JavaPlugin plugin, String dirName, String name) {
        File dir = FileUtils.createDirIfNotExist(plugin, dirName);
        File yamlFile = new File(dir, name + ".yml");
        if(yamlFile.exists() && ! yamlFile.isDirectory()) return new YamlData(mainName(yamlFile.getName()), yamlFile, YamlConfiguration.loadConfiguration(yamlFile));
        return null;
    }

    @SneakyThrows
    public static YamlData findDataOrCreateFromDir(JavaPlugin plugin, String dirName, String name) {
        File dir = FileUtils.createDirIfNotExist(plugin, dirName);
        File yamlFile = new File(dir, name + ".yml");
        if(!yamlFile.exists() || yamlFile.isDirectory()) yamlFile.createNewFile();
         return new YamlData(mainName(yamlFile.getName()), yamlFile, YamlConfiguration.loadConfiguration(yamlFile));
    }

    @SneakyThrows
    public static YamlData findDataOrCreateFromDir(File dir, String name) {
        File yamlFile = new File(dir, name + ".yml");
        if(!yamlFile.exists() || yamlFile.isDirectory()) yamlFile.createNewFile();
        return new YamlData(mainName(yamlFile.getName()), yamlFile, YamlConfiguration.loadConfiguration(yamlFile));
    }


    public static String mainName(String fileName) {
        if (null == fileName) {
            return null;
        }
        int len = fileName.length();
        if (0 == len) {
            return fileName;
        }
        if (CharUtil.isFileSeparator(fileName.charAt(len - 1))) {
            len--;
        }

        int begin = 0;
        int end = len;
        char c;
        for (int i = len - 1; i >= 0; i--) {
            c = fileName.charAt(i);
            if (len == end && CharUtil.DOT == c) {
                // 查找最后一个文件名和扩展名的分隔符：.
                end = i;
            }
            // 查找最后一个路径分隔符（/或者\），如果这个分隔符在.之后，则继续查找，否则结束
            if (CharUtil.isFileSeparator(c)) {
                begin = i + 1;
                break;
            }
        }

        return fileName.substring(begin, end);
    }
}
