package com.power.common.util;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * OkHttp3Util单元测试
 *
 * @author yu 2018/06/19.
 */
public class OkHttp3UtilTest {

    @Test
    public void testSyncGet() {
        String url = "http://www.baidu.com";
        Map<String,String> params = new HashMap<>();
        params.put("ok","ok");
        System.out.println(OkHttp3Util.syncGet(url,params));
    }

    @Test
    public void testAsyncGet() {
        String url = "http://www.baidu.com";

        Map<String,String> map = new HashMap<>();
        map.put("ok","ok");
        OkHttp3Util.asyncPost(url,map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //do something
                System.out.println("Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("resp");
                if (response.isSuccessful()) {
                    //do something
                    String result = response.body().string();
                    System.out.println("result:" + result);
                } else {
                    throw new IOException(response + "");
                }
            }
        });
    }
}
