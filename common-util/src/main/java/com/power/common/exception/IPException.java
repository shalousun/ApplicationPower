package com.power.common.exception;

import com.power.common.interfaces.IMessage;

/**
 * @author yu 2018/9/28.
 */
public class IPException extends BaseRuntimeException {

    public IPException(String message) {
        super(message);
    }

    public IPException(IMessage iMessage) {
        super(iMessage);
    }

    public IPException(IMessage errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
