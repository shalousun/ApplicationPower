package com.power.doc.model;

import java.util.List;

/**
 * Description:
 * Api配置
 *
 * @author yu 2018/06/18.
 */
public class ApiConfig {

    /**
     * 是否采用严格模式
     */
    private boolean isStrict;

    /**
     * 输出路径
     */
    private String outPath;

    /**
     * 请求头
     */
    private List<ApiRequestHeader> requestHeaders;

    /**
     * 自定义字段
     */
    private List<CustomResponseField> customResponseFields;


    public boolean isStrict() {
        return isStrict;
    }

    public void setStrict(boolean strict) {
        isStrict = strict;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public List<ApiRequestHeader> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(List<ApiRequestHeader> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public List<CustomResponseField> getCustomResponseFields() {
        return customResponseFields;
    }

    public void setCustomResponseFields(List<CustomResponseField> customResponseFields) {
        this.customResponseFields = customResponseFields;
    }
}
