package com.power.doc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 字段属性的文档
 *
 * @author yu 2018/06/11.
 */
public class FieldDoc extends AbstractComment {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;


    /**
     * 字段中的字段信息
     */
    private List<FieldDoc> fieldDocList = new ArrayList<>();


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public List<FieldDoc> getFieldDocList() {
        return fieldDocList;
    }

    public void setFieldDocList(List<FieldDoc> fieldDocList) {
        this.fieldDocList = fieldDocList;
    }
}
