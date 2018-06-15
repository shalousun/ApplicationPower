package com.power.doc.model;

/**
 * Description:
 * 方法的文档
 *
 * @author yu 2018/06/11.
 */
public class MethodDoc extends AbstractComment{

    /**
     * HTTP请求方法
     */
    private String requestMethod;
    /**
     * 请求地址URI
     */
    private String uri;

    private FieldDoc methodArgumentComment;

    private FieldDoc methodReturnComment;

    /**
     * 返回值JSON
     */
    private String methodReturnTypeCommentJson;
    /**
     * 参数JSON
     */
    private String methodArgumentCommentJson;


    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public FieldDoc getMethodArgumentComment() {
        return methodArgumentComment;
    }

    public void setMethodArgumentComment(FieldDoc methodArgumentComment) {
        this.methodArgumentComment = methodArgumentComment;
    }

    public FieldDoc getMethodReturnComment() {
        return methodReturnComment;
    }

    public void setMethodReturnComment(FieldDoc methodReturnComment) {
        this.methodReturnComment = methodReturnComment;
    }

    public String getMethodReturnTypeCommentJson() {
        return methodReturnTypeCommentJson;
    }

    public void setMethodReturnTypeCommentJson(String methodReturnTypeCommentJson) {
        this.methodReturnTypeCommentJson = methodReturnTypeCommentJson;
    }

    public String getMethodArgumentCommentJson() {
        return methodArgumentCommentJson;
    }

    public void setMethodArgumentCommentJson(String methodArgumentCommentJson) {
        this.methodArgumentCommentJson = methodArgumentCommentJson;
    }
}
