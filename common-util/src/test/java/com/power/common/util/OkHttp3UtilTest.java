package com.power.common.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * OkHttp3Util单元测试,单元测试中不支持OkHttp3的异步请求测试
 *
 * @author yu 2018/06/19.
 */
public class OkHttp3UtilTest {

    @Test
    public void testSyncGet() {
        String url = "http://www/baidu.com";
        Map<String, String> params = new HashMap<>();
        params.put("res", "xx");
        params.put("ver", "ver_0.0.1");
        System.out.println(OkHttp3Util.syncGet(url, params));
    }
}
