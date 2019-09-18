package com.power.common.util;

import com.power.common.net.SSLSocketFactoryBuilder;
import com.power.common.net.TrustAnyTrustManager;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http请求工具类
 *
 * @author yu 2018/06/19.
 */
public class OkHttp3Util {

    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FORM_DATA = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttp3Util.class);

    /**
     * Simple sync get request.
     *
     * @param url request url
     * @return response body
     */
    public static String syncGet(String url) {
        return doSyncGet(url, null, null);
    }

    /**
     * Simple async get request.
     *
     * @param url      request
     * @param callback call back
     */
    public static void asyncGet(String url, Callback callback) {
        doAsyncGet(url, null, null, callback);
    }

    /**
     * Synchronous get request with parameters
     *
     * @param baseUrl request base url
     * @param params  request params
     * @return response body
     */
    public static String syncGet(String baseUrl, Map<String, String> params) {
        return doSyncGet(baseUrl, params, null);
    }

    /**
     * Asynchronous get request with parameters
     *
     * @param baseUrl  request base url
     * @param params   request params
     * @param callback callback
     */
    public static void asyncGet(String baseUrl, Map<String, String> params, Callback callback) {
        doAsyncGet(baseUrl, params, null, callback);
    }

    /**
     * Synchronous get request with parameters and headers
     *
     * @param baseUrl request base url
     * @param params  request params
     * @param headers request headers
     * @return response body
     */
    public static String syncGet(String baseUrl, Map<String, String> params, Map<String, String> headers) {
        return doSyncGet(baseUrl, params, headers);
    }

    /**
     * Asynchronous get request with parameters and headers
     *
     * @param baseUrl  request base url
     * @param params   request params
     * @param headers  request headers
     * @param callback call back
     */
    public static void asyncGet(String baseUrl, Map<String, String> params, Map<String, String> headers,
                                Callback callback) {
        doAsyncGet(baseUrl, params, headers, callback);
    }

    /**
     * Synchronous post request with parameters
     *
     * @param url    request url
     * @param params request params
     * @return response body
     */
    public static String syncPost(String url, Map<String, String> params) {
        return doSyncPost(url, params, null);
    }

    /**
     * Asynchronous post request with parameters
     *
     * @param url      request url
     * @param params   request params
     * @param callback call back
     */
    public static void asyncPost(String url, Map<String, String> params, Callback callback) {
        doAsyncPost(url, params, null, callback);
    }

    /**
     * Synchronous post request with parameters and headers
     *
     * @param url     url
     * @param params  request params
     * @param headers request headers
     * @return response body
     */
    public static String syncPost(String url, Map<String, String> params, Map<String, String> headers) {
        return doSyncPost(url, params, headers);
    }


    /**
     * Asynchronous post request with parameters and headers
     *
     * @param url      request url
     * @param params   request params
     * @param headers  request headers
     * @param callback call back
     */
    public static void asyncPost(String url, Map<String, String> params, Map<String, String> headers, Callback callback) {
        doAsyncPost(url, params, headers, callback);
    }


    /**
     * Synchronous post json request
     *
     * @param url  request url
     * @param json json data
     * @return response body
     */
    public static String syncPostJson(String url, String json) {
        RequestBody body = RequestBody.create(JSON_TYPE, json);
        return doSyncPost(url, body, null);
    }

    /**
     * Asynchronous post json request
     *
     * @param url      request url
     * @param json     json data
     * @param callback call back
     */
    public static void asyncPostJson(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON_TYPE, json);
        doAsyncPost(url, body, null, callback);
    }

    /**
     * Synchronous post json request with headers
     *
     * @param url        request url
     * @param json       json data
     * @param headersMap request headers
     * @return response body
     */
    public static String syncPostJson(String url, String json, Map<String, String> headersMap) {
        RequestBody body = RequestBody.create(JSON_TYPE, json);
        LOGGER.debug("OkHttp3 sync json param:{} ", json);
        return doSyncPost(url, body, headersMap);
    }

    /**
     * Asynchronous post json request with headers
     *
     * @param url        request url
     * @param json       json data
     * @param headersMap request headers
     * @param callback   call back
     */
    public static void asyncPostJson(String url, String json, Map<String, String> headersMap, Callback callback) {
        RequestBody body = RequestBody.create(JSON_TYPE, json);
        LOGGER.debug("OkHttp3 async post json param:{} ", json);
        doAsyncPost(url, body, headersMap, callback);
    }

    /**
     * Asynchronous get request with parameters and headers
     *
     * @param client     OkHttpClient
     * @param baseUrl    request base url
     * @param params     request params
     * @param headersMap request headers
     * @param callback   callback
     */
    public static void asyncGet(OkHttpClient client, String baseUrl, Map<String, String> params, Map<String, String> headersMap,
                                Callback callback) {
        String url = UrlUtil.urlJoin(baseUrl, params);
        LOGGER.debug("OkHttp3 async get url:{}", url);
        Request request;
        if (null == headersMap || headersMap.size() == 0) {
            request = new Request.Builder().url(url).build();
        } else {
            Headers headers = setHeaders(headersMap);
            request = new Request.Builder().url(url).headers(headers).build();
        }
        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    /**
     * Asynchronous post json request with headers
     *
     * @param client     OkHttpClient
     * @param url        request url
     * @param body       OkHttp3 RequestBody
     * @param headersMap request headers
     * @param callback   call back
     */
    public static void asyncPost(OkHttpClient client, String url, RequestBody body, Map<String, String> headersMap,
                                 Callback callback) {
        LOGGER.debug("OkHttp3 async post url:{}", url);
        Request request;
        if (null == headersMap || headersMap.size() == 0) {
            request = new Request.Builder().post(body).url(url).build();
        } else {
            Headers headers = setHeaders(headersMap);
            request = new Request.Builder().post(body).url(url).headers(headers).build();
        }
        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    private static void doAsyncGet(String baseUrl, Map<String, String> params, Map<String, String> headersMap,
                                   Callback callback) {
        OkHttpClient client = OkHttp3Util.getInstance();
        String url = UrlUtil.urlJoin(baseUrl, params);
        LOGGER.debug("OkHttp3 async get url:{}", url);
        Request request;
        if (null == headersMap || headersMap.size() == 0) {
            request = new Request.Builder().url(url).build();
        } else {
            Headers headers = setHeaders(headersMap);
            request = new Request.Builder().url(url).headers(headers).build();
        }
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    private static String doSyncGet(String baseUrl, Map<String, String> params, Map<String, String> headersMap) {
        OkHttpClient client = OkHttp3Util.getInstance();
        String url = UrlUtil.urlJoin(baseUrl, params);
        System.out.println(url);
        LOGGER.debug("SyncGet Request url: {}", url);
        long startTime = System.currentTimeMillis();
        Request request;
        if (null == headersMap || headersMap.size() == 0) {
            request = new Request.Builder().url(url).build();
        } else {
            Headers headers = setHeaders(headersMap);
            request = new Request.Builder().url(url).headers(headers).build();
        }
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseBody = response.body().string();
            LOGGER.debug("SyncGet Response body is:{}", responseBody);
            LOGGER.debug("SyncGet Cost time:", (System.currentTimeMillis() - startTime));
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void doAsyncPost(String url, Map<String, String> params, Map<String, String> headersMap, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody body = builder.build();
        doAsyncPost(url, body, headersMap, callback);
    }

    private static String doSyncPost(String url, Map<String, String> params, Map<String, String> headersMap) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody body = builder.build();
        return doSyncPost(url, body, headersMap);
    }

    private static void doAsyncPost(String url, RequestBody body, Map<String, String> headersMap, Callback callback) {
        OkHttpClient client = OkHttp3Util.getInstance();
        Request request;
        if (null == headersMap || headersMap.size() == 0) {
            request = new Request.Builder().post(body).url(url).build();
        } else {
            Headers headers = setHeaders(headersMap);
            request = new Request.Builder().post(body).url(url).headers(headers).build();
        }
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    private static String doSyncPost(String url, RequestBody body, Map<String, String> headersMap) {
        OkHttpClient client = OkHttp3Util.getInstance();
        Request request;
        if (null == headersMap || headersMap.size() == 0) {
            request = new Request.Builder().post(body).url(url).build();
        } else {
            Headers headers = setHeaders(headersMap);
            request = new Request.Builder().post(body).url(url).headers(headers).build();
        }
        LOGGER.debug("Request url: {}", url);
        long startTime = System.currentTimeMillis();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseBody = response.body().string();
            LOGGER.debug("Response body is:{}", responseBody);
            LOGGER.debug("Cost time:", (System.currentTimeMillis() - startTime));
            return responseBody;
        } catch (IOException e) {
            throw new RuntimeException("Can't connect server", e);
        }
    }

    private static Headers setHeaders(Map<String, String> headersParams) {
        Headers headers;
        okhttp3.Headers.Builder headersBuilder = new okhttp3.Headers.Builder();
        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersBuilder.add(key, headersParams.get(key));
            }
        }
        headers = headersBuilder.build();
        return headers;
    }

    public static OkHttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OkHttpClient INSTANCE = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketFactoryBuilder.getSslSocketFactory(), new TrustAnyTrustManager())
                .build();
    }
}
