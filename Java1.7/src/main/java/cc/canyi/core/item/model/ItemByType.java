package cc.canyi.core.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ItemByType {
    private String type;
    private String name;
    private List<String> lore;
    private short dur;

    @Override
    public ItemByType clone() {
        return new ItemByType(type, name, lore, dur);
    }
}
