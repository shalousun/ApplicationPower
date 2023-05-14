package com.power.common.interfaces;

/**
 * @author yu 2018/12/12.
 */
public interface IMessage {

    /**
     * error code
     *
     * @return String
     */
    String getCode();

    /**
     * error message
     *
     * @return String
     */
    String getMessage();
}
