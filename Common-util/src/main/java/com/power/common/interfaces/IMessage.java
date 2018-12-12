package com.power.common.interfaces;

/**
 * @author yu 2018/12/12.
 */
public interface IMessage {

    /**
     * 响应码
     * @return
     */
    String getCode();

    /**
     * 获取响应信息
     * @return
     */
    String getMessage();
}
