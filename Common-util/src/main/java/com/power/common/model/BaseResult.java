package com.power.common.model;


import java.io.Serializable;

/**
 * @author yu 2018/11/30.
 */
public abstract class BaseResult<T> implements Serializable {

    /**
     * 是否成功
     *
     */
    private boolean success = false;

    /**
     * 错误提示(成功succeed)
     */
    private String message;

    /**
     * 成功返回的数据
     */
    private T data;

    /**
     * 错误代码
     */
    private String code;

    /**
     * 响应时间
     */
    private String timestamp;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}