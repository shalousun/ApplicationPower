package com.power.common.exception;

/**
 *
 * @author yu 2019/10/29.
 */
public class AssertException extends RuntimeException {

    public AssertException(String message) {
        super(message);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

}
