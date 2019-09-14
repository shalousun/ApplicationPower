package com.power.common.exception;

import com.power.common.interfaces.IMessage;

/**
 * @author yu 2019/1/21.
 */
public class BaseRuntimeException extends RuntimeException implements IMessage {

    private String errorCode;

    protected BaseRuntimeException(IMessage iMessage) {
        super(iMessage.getMessage());
        this.errorCode = iMessage.getCode();
    }

    protected BaseRuntimeException(IMessage iMessage, String message) {
        super(message);
        this.errorCode = iMessage.getCode();
    }


    protected BaseRuntimeException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    @Override
    public String getCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
