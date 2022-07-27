package cc.canyi.core.utils;

import cc.canyi.core.RuomoeCorePlugin;
import cc.canyi.core.papi.PAPIBuilder;
import cc.canyi.core.papi.model.PAPI;
import lombok.Setter;

public class PAPIUtils {
    @Setter
    private static boolean isPapiLoaded;

    public static PAPI.PAPIBuilder createBuilder() {
        return PAPIBuilder.create();
    }

    public static void register(PAPI papi) {
        if(isPapiLoaded) {
            if(papi.register()) RuomoeCorePlugin.getInfoLogger().info("PAPI Hooker " + papi.getIdentifier() + " has register");
            else RuomoeCorePlugin.getInfoLogger().warning("PAPI Hooker " + papi.getIdentifier() + " register error!");
        }
    }
}
