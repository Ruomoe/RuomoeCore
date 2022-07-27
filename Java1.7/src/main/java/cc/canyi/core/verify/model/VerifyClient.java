package cc.canyi.core.verify.model;

import lombok.Data;

@Data
public class VerifyClient {
    private final String url;
    private final int port;
    private final String path;
}
