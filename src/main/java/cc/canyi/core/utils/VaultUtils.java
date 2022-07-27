package cc.canyi.core.utils;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.model.IEconomy;

public class VaultUtils {
    public static IEconomy getEconomy() {
        return RuomoeCorePlugin.getDefaultEconomy();
    }
}

