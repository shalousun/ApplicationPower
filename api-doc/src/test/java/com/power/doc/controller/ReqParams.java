package com.power.doc.controller;


/**
 * 请求参数
 * @author yu
 */
public class ReqParams<T> {

    /**
     * 调用链id,用于全链路跟踪
     */
    private String traceId;

    /**
     * 合作伙伴ID
     */
    private String partnerId;


    private String partnerKey;

    /**
     * token
     */
    private String token;


    /**
     * 请求时间
     */
    private String time;


    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 请求数据
     */
    public T data;



    //getters and setters
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }
}
