package cc.canyi.core.papi.model;

import lombok.Builder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

@Builder
public class PAPI extends PlaceholderExpansion {

    private String identifier;
    private String author;
    private String version;
    private String plugin;

    private IPAPIAgent ipapiAgent;

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getPlugin() {
        return plugin;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        return ipapiAgent.callback(p, params);
    }
}
