package com.power.common.exception;

/**
 * Description:
 * Xss攻击的exception
 *
 * @author yu 2018/06/05.
 */
public class XssException extends RuntimeException {

    public XssException(String message) {
        super(message);
    }
}
