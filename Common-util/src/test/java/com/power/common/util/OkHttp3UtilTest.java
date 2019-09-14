package com.power.common.util;

import org.junit.Test;

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
        String url = "http://smarthome.dq.ott4china.com:8899/iptv_oms_center/ProvinceControl";
        Map<String, String> params = new HashMap<>();
        params.put("res", "mnLue%2FPmm7oHWsqfMeWJC9svT6OsJ2wG4VkjAhQ8IAYT2%2FpRviOQpG%2FuDc1T7RrhAJgq34Zdrh%2FbyxwyZONt5XiPVVCs9Ahtcwb5ePFldwhsP8z51ICKQj3XDnPyQB5VDt83FQJ%2FgaxDBZSC0cA5Bw%3D%3D");
        params.put("ver", "ver_0.0.1");
        System.out.println(OkHttp3Util.syncGet(url, params));
    }
}
