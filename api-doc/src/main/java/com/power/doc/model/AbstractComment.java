package com.power.doc.model;



/**
 * @author Frank
 */

public abstract class AbstractComment {
    /**
     * innerText
     */
    private String comment;
    /**
     * innerHTML
     */
    private String rawComment;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRawComment() {
        return rawComment;
    }

    public void setRawComment(String rawComment) {
        this.rawComment = rawComment;
    }
}
