package cc.canyi.core.papi;

import cc.canyi.core.papi.model.PAPI;

public class PAPIBuilder {
    public static PAPI.PAPIBuilder create() {
        return PAPI.builder();
    }
}
