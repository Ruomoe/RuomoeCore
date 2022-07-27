package cc.canyi.core.model;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import lombok.Setter;

public class ProtocolManager {
    @Getter
    @Setter
    private com.comphenix.protocol.ProtocolManager protocolManager;

    public void init() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }
}
