package com.power.doc.model;

import java.util.List;

public class ApiDoc {

    private String name;

    private List<ApiMethodDoc> list;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApiMethodDoc> getList() {
        return list;
    }

    public void setList(List<ApiMethodDoc> list) {
        this.list = list;
    }
}
