package com.power.common.model;


import java.io.Serializable;

/**
 * @author yu 2018/11/30.
 */
public abstract class BaseResult<T> implements Serializable {

    /**
     * Is successful
     */
    private boolean success = false;

    /**
     * Error message (while success is true return succeed)
     */
    private String message;

    /**
     * Return data
     */
    private T data;

    /**
     * Error code
     */
    private String code;

    /**
     * Response time
     */
    private String timestamp;

    /**
     * Trace  ID
     */
    private String traceId;


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

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}