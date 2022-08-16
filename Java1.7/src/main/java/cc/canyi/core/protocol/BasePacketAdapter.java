package cc.canyi.core.protocol;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.plugin.BukkitPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public abstract class BasePacketAdapter extends PacketAdapter {
    private final Map<Object, Object> repMap;
    private final boolean isPAPI;

    public BasePacketAdapter(final BukkitPlugin plugin, final Map<Object, Object> repMap, final boolean isPAPI, final PacketType... type) {
        super(plugin, type);
        this.repMap = repMap;
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
        this.isPAPI = isPAPI;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        try{
            onReplace(event);
        }catch (Throwable e) {
            RuomoeCorePlugin.getInfoLogger().warning("BasePacketAdapter error sender " + event.getPacketID() + " | " + event.getPacketType().name() + " | " + event.getPlayer().getName());
            e.printStackTrace();
        }
    }
    public void onReplace(final PacketEvent e) {
        final PacketContainer packet = e.getPacket();
        final WrappedChatComponent wcp = packet.getChatComponents().read(0);
        if (wcp == null) { return; }
        wcp.setJson(replace(e.getPlayer(), wcp.getJson()));
        packet.getChatComponents().write(0, wcp);
    }

    public ItemStack replaceItem(final Player player, final ItemStack item) {
        if (item == null) { return null; }
        if (item.hasItemMeta()) {
            final ItemMeta im = item.getItemMeta();
            if (im.hasDisplayName()) {
                im.setDisplayName(replace(player, im.getDisplayName()));
            }
            if (im.hasLore()) {
                im.setLore(replace(player, im.getLore()));
            }
            item.setItemMeta(im);
        }
        return item;
    }

    public ItemStack[] replaceItem(final Player player, final ItemStack[] items) {
        for (int i = 0; i < items.length; i++) {
            items[i] = replaceItem(player, items[i]);
        }
        return items;
    }

    public List<ItemStack> replaceItem(Player player, List<ItemStack> iam) {
        for (int i = 0; i < iam.size(); i++) {
            iam.set(i, replaceItem(player, iam.get(i)));
        }
        return iam;
    }

    public List<String> replace(final Player player, final List<String> strs) {
        for (int i = 0; i < strs.size(); i++) {
            strs.set(i, replace(player, strs.get(i)));
        }
        return strs;
    }

    public String replace(final Player player, String str) {
        if (repMap != null) {
            for (final Map.Entry<Object, Object> entry : repMap.entrySet()) {
                str = str.replace(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        return player != null && isPAPI ? PlaceholderAPI.setPlaceholders(player, str) : str;
    }

    public String[] replace(final Player player, final String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            strs[i] = replace(player, strs[i]);
        }
        return strs;
    }
}
