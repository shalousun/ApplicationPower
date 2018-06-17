package com.power.doc.model;

/**
 * Description:
 * 请求头
 *
 * @author yu 2018/06/18.
 */
public class ApiRequestHeader {

    /**
     * 请求头的名称
     */
    private String name;

    /**
     * 请求头类型
     */
    private String type;
    /**
     * 请求头描述
     */
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
