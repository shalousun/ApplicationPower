package com.power.common.util;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * okhttp3异步测试
 *
 * @author yu 2019/3/18.
 */
public class OkHttp3UtilAsyncTest {

    public static void main(String[] args) {
        String url = "http://www.baidu.com";

        Map<String, String> map = new HashMap<>();
        map.put("ok", "ok");
        OkHttp3Util.asyncGet(url, map, new Callback() {
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
                    System.out.println(result);
                } else {
                    throw new IOException(response + "");
                }
            }
        });
    }
}
