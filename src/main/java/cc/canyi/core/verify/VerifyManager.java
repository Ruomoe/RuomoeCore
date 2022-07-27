package cc.canyi.core.verify;

import cc.canyi.core.verify.model.VerifyClient;
import org.bukkit.plugin.Plugin;

public class VerifyManager {
    public static String verify(Plugin plugin, String code, VerifyClient client) {
        return HttpUtils.getResultFormHttp(plugin.getName(), code, client);
    }

}
