package com.power.common.constants;

import com.power.common.inferfaces.IMessage;

/**
 * @author yu 2018/11/30.
 */
public interface BaseErrorCode {

    enum common implements IMessage {

        SUCCESS("0000", "succeed"),

        PARAM_EMPTY("1001", "必选参数为空"),

        PARAM_ERROR("1002", "参数格式错误"),

        UNKNOWN_ERROR("9999", "系统繁忙，请稍后再试....");

        private String code;
        private String message;


        common(String errCode, String errMsg) {
            this.code = errCode;
            this.message = errMsg;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }
}
