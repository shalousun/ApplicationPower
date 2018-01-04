package com.power.doc.model;

import java.io.Serializable;

/**
 * api文档
 */
public class ApiMethodDoc implements Serializable {

    private String desc;

    private String url;

    private String type;

    private String requestParams;

    private String responseUsage;

    private String responseParams;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(null != url){
            this.url = url;
        }
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getResponseUsage() {
        return responseUsage;
    }

    public void setResponseUsage(String responseUsage) {
        this.responseUsage = responseUsage;
    }

    public String getResponseParams() {
        return responseParams;
    }

    public void setResponseParams(String responseParams) {
        this.responseParams = responseParams;
    }
}
