package cc.canyi.core.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ItemByID {
    private int id;
    private String name;
    private List<String> lore;
    private short dur;

    @Override
    public ItemByID clone() {
        return new ItemByID(id, name, lore, dur);
    }
}
