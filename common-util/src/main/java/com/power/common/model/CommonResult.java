package com.power.common.model;

import com.power.common.constants.BaseErrorCode;
import com.power.common.interfaces.IMessage;
import com.power.common.util.DateTimeUtil;

import java.io.Serializable;
import java.util.UUID;

/**
 * Common Result
 *
 * @author sunyu
 */
public class CommonResult<T> extends BaseResult<T> implements Serializable {

    /**
     * serialVersionUID:.
     */
    private static final long serialVersionUID = -7268040542410707954L;


    /**
     * Default constructor
     */
    public CommonResult() {

    }

    /**
     * @param success the success
     * @param message the message
     */
    public CommonResult(boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    /**
     * @param success the success
     */
    public CommonResult(boolean success) {
        this.setSuccess(success);
    }

    /**
     * @param code    error code
     * @param message success or error messages
     */
    public CommonResult(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    /**
     * @param success the success
     * @param message success or error messages
     * @param data    response data while true
     */
    public CommonResult(boolean success, String message, T data) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setData(data);
    }

    /**
     * Successful response (default response code is 0000)
     *
     * @return CommonResult
     */
    public static <T> CommonResult<T> ok() {
        return ok(BaseErrorCode.Common.SUCCESS);
    }

    /**
     * Customize success response message,
     * generally define enumeration to implement IMessage
     *
     * @param message IMessage interface
     * @param <T>     Object
     * @return CommonResult
     */
    public static <T> CommonResult<T> ok(IMessage message) {
        return baseCreate(message.getCode(), message.getMessage(), true);
    }

    /**
     * Server unknown exception (default response code 9999)
     *
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail() {
        return fail(BaseErrorCode.Common.UNKNOWN_ERROR);
    }

    /**
     * Failed response
     *
     * @param message IMessage
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail(IMessage message) {
        return fail(message.getCode(), message.getMessage());
    }

    /**
     * 失败或失败响应
     *
     * @param code    the error code
     * @param message error message
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail(String code, String message) {
        return baseCreate(code, message, false);
    }

    private static <T> CommonResult<T> baseCreate(String code, String msg, boolean success) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setSuccess(success);
        result.setMessage(msg);
        result.setTimestamp(DateTimeUtil.nowStrTime());
        result.setTraceId(UUID.randomUUID().toString());
        return result;
    }

    /**
     * Add trace ID
     *
     * @param traceId Trace ID
     * @return
     */
    public CommonResult<T> addTraceId(String traceId) {
        this.setTraceId(traceId);
        return this;
    }

    /**
     * Usage:
     * Result.ok().setResult("hello")
     * Business data returned when processing is successful
     *
     * @param data you return data
     * @return CommonResult
     */
    public CommonResult<T> setResult(T data) {
        this.setData(data);
        return this;
    }

    /**
     * Usage:
     * Result.ok().addData("hello")
     * Business data returned when processing is successful
     *
     * @param data you return data
     * @return CommonResult
     */
    public CommonResult<T> addData(T data) {
        this.setData(data);
        return this;
    }


    /**
     * Override the supper method
     *
     * @return T
     */
    public T getData() {
        return super.getData();
    }


}
