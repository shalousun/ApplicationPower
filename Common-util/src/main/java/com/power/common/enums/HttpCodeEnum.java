package com.power.common.enums;

import com.power.common.interfaces.IMessage;

/**
 * 常用http状态码
 */
public enum HttpCodeEnum implements IMessage {
    SUCCESS("200","ok"),
    BAD_REQUEST("400","Bad Request"),
    UNAUTHORIZED("401","Unauthorized"),
    FORBIDDEN("403","Forbidden"),
    NOT_FOUND("404","Not Found"),
    UNSUPPORTED_MEDIA_TYPE("415","Unsupported Media Type"),
    INTERNAL_SERVER_ERROR("500","Internal Server Error"),
    BAD_GATEWAY("502","Bad Gateway"),
    SERVICE_UNAVAILABLE("503","Service Unavailable");


    public String code;

    public String message;

    HttpCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
