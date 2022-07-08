package cc.canyi.core.nms.model;

import lombok.Data;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
public class ShowItem {
    private final ItemStack stack;

    public HoverEvent hoverEvent() {
        return new HoverEvent(HoverEvent.Action.SHOW_ITEM, new BaseComponent[]{new TextComponent(getJsonMessage(stack))});
    }

    //物品Json化

    @SneakyThrows
    private static String getJsonMessage(ItemStack itemStack) {

        Class<?> clazz1 = Class.forName("inventory.CraftItemStack");
        Class clazz2 = Class.forName("ItemStack");
        Class clazz3 = Class.forName("NBTTagCompound");

        Method method1, method2;

        try {
            method1 = clazz1.getMethod("asNMSCopy", ItemStack.class);

            method2 = clazz2.getMethod("save", clazz3);

        } catch (NoSuchMethodException | NullPointerException e) {
            //NMS错误
            return null;
        }
        Object result;
        try {
            result = method2.invoke(method1.invoke(null, itemStack), clazz3.newInstance());

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NullPointerException e) {
            //NMS错误
            return null;
        }
        return result.toString();

    }
}
