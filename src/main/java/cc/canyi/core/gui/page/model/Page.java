package cc.canyi.core.gui.page.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class Page {
    private final int index;
    private final HashMap<Integer, Object> dataMap;
    private int size = 0;

    public Object getObjectBySlot(int slot) {
        return dataMap.get(slot);
    }

    public void putObject(int slot, Object o) {
        dataMap.put(slot, o);
        size ++;
    }

    public void print() {
        System.out.println("Page index: " + index);
        System.out.println("Page data: " + dataMap);
    }
}
