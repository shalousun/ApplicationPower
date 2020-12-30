package com.power.common.net;

/**
 * @author yu 2019/3/25.
 */
public class NetWorkTimeout {

    /**
     * http or https read timeout (milliseconds)
     */
    private long readTimeout;

    /**
     * http or https write timeout (milliseconds)
     */
    private long writeTimeout;

    /**
     * http or https connection timeout, in milliseconds
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
