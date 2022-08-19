package cc.canyi.core.yaml.model;

import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Data
public class YamlData {
    private final String name;
    private final File file;
    private final YamlConfiguration yaml;

    @SneakyThrows
    public void saveChange() {
        yaml.save(file);
    }

    public boolean delete() {
        return file.delete();
    }

}
