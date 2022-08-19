package cc.canyi.core.yaml.model;


import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class YamlDataTable {
    private final File dir;
    private final List<YamlData> dataList;
}
