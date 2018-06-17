package com.power.doc.model;

/**
 * Description:
 * Api 自动义字段修正
 *
 * @author yu 2018/06/18.
 */
public class CustomResponseField {

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段描述
     */
    private String description;

    /**
     * 字段隶属类
     */
    private String ownerClassName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerClassName() {
        return ownerClassName;
    }

    public void setOwnerClassName(String ownerClassName) {
        this.ownerClassName = ownerClassName;
    }
}
