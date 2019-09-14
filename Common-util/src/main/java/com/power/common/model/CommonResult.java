package com.power.common.model;

import com.power.common.constants.BaseErrorCode;
import com.power.common.interfaces.IMessage;
import com.power.common.util.DateTimeUtil;

import java.io.Serializable;

/**
 * 公共返回结果
 *
 * @author sunyu
 */
public class CommonResult<T> extends BaseResult implements Serializable {

    /**
     * serialVersionUID:.
     */
    private static final long serialVersionUID = -7268040542410707954L;


    /**
     * 默认构造器
     */
    public CommonResult() {

    }

    /**
     * @param success 是否成功
     * @param message 返回的消息
     */
    public CommonResult(boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    /**
     * @param success 是否成功
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
     * @param success 是否成功
     * @param message 消息
     * @param data    数据
     */
    public CommonResult(boolean success, String message, T data) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setData(data);
    }

    /**
     * 通用成功响应(默认响应码为0000)
     *
     * @return CommonResult
     */
    public static CommonResult ok() {
        return ok(BaseErrorCode.Common.SUCCESS);
    }

    /**
     * 自动义成功响应，一般定义枚举实现IMessage
     *
     * @param msg IMessage interface
     * @param <T> Object
     * @return CommonResult
     */
    public static <T> CommonResult<T> ok(IMessage msg) {
        return baseCreate(msg.getCode(), msg.getMessage(), true);
    }

    /**
     * 通用服务器未知异常(默认响应码9999)
     *
     * @return CommonResult
     */
    public static CommonResult fail() {
        return fail(BaseErrorCode.Common.UNKNOWN_ERROR);
    }

    /**
     * 失败响应
     *
     * @param message IMessage
     * @return CommonResult
     */
    public static CommonResult fail(IMessage message) {
        return fail(message.getCode(), message.getMessage());
    }

    /**
     * 失败或失败响应
     *
     * @param code 错误响应码
     * @param msg  错误信息
     * @return CommonResult
     */
    public static CommonResult fail(String code, String msg) {
        return baseCreate(code, msg, false);
    }

    private static <T> CommonResult<T> baseCreate(String code, String msg, boolean success) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setSuccess(success);
        result.setMessage(msg);
        result.setTimestamp(DateTimeUtil.nowStrTime());
        return result;
    }

    /**
     * Usage:
     * Result.ok().setResult("hello")
     * 返回结果时携带数据
     *
     * @param data you return data
     * @return CommonResult
     */
    public CommonResult<T> setResult(T data) {
        this.setData(data);
        return this;
    }

    /**
     * 覆盖supper方法
     *
     * @return T
     */
    public T getData() {
        return (T) super.getData();
    }


}
