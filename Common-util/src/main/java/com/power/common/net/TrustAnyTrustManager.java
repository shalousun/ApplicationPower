package com.power.common.net;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * @author yu 2019/1/18.
 */
public class TrustAnyTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[]{};
    }
}
