package cc.canyi.core.verify;

import cc.canyi.core.verify.model.VerifyClient;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;

public class HttpUtils {
    public static String getResultFormHttp(String pluginName, String code, VerifyClient client) {
        return getResultFormHttp(pluginName, code, client, 5000);
    }
    public static String getResultFormHttp(String pluginName, String code, VerifyClient client, int timeout) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("plugin", pluginName);
            params.put("code", code);
            String url = client.getUrl() + ":" + client.getPort() + "/" + client.getPath();
            String get = HttpUtil.get(url, params, timeout);
            return JSONUtil.parseObj(get).getStr("encode");
        }catch (Exception e) {
            return "ERROR";
        }
    }
}
