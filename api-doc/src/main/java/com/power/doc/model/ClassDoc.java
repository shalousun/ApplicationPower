package com.power.doc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 类的api 文档
 *
 * @author yu 2018//11.
 */
public class ClassDoc extends AbstractComment {



    private List<MethodDoc> methodCommentList = new ArrayList<>();


    public List<MethodDoc> getMethodCommentList() {
        return methodCommentList;
    }

    public void setMethodCommentList(List<MethodDoc> methodCommentList) {
        this.methodCommentList = methodCommentList;
    }
}
