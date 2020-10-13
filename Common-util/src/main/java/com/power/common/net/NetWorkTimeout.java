package com.power.common.net;

/**
 * @author yu 2019/3/25.
 */
public class NetWorkTimeout {

    /**
     * http或者https读取超时时间，单位为毫秒
     */
    private long readTimeout;

    /**
     * http或者https写超时，单位为毫秒
     */
    private long writeTimeout;

    /**
     * http或者https连接超时，，单位为毫秒
     */
    private long connectTimeout;


    public static NetWorkTimeout builder(){
        return new NetWorkTimeout();
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public NetWorkTimeout setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public NetWorkTimeout setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public NetWorkTimeout setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }
}
