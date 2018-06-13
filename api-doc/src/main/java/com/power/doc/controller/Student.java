package com.power.doc.controller;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * 学生信息
 *
 * @author yu 2018/06/11.
 */
public class Student {

    /**
     * 姓名
     */
    private String stuName;

    /**
     * 年龄
     */
    private int stuAge;

    /**
     * 地址
     */
    private String stuAddress;

    /**
     * 用户对象
     */
    private User user;

    /**
     *
     */
    private Map<String,User> userMap;

    /**
     * 用户对象2
     */
    private User user1;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getStuAge() {
        return stuAge;
    }

    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }

    public String getStuAddress() {
        return stuAddress;
    }

    public void setStuAddress(String stuAddress) {
        this.stuAddress = stuAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }
}
