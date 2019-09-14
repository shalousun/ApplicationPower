package com.power.common.exception;

/**
 * @author yu 2018/9/28.
 */
public class IPException extends RuntimeException {

    public IPException(String message) {
        super(message);
    }

    public IPException(String message, Throwable cause) {
        super(message, cause);
    }

    public IPException(Throwable cause) {
        super(cause);
    }
}
