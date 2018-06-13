package com.power.doc.controller;

/**
 * Description:
 * 用户对象
 *
 * @author yu 2018/06/12.
 */
public class User {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户年龄
     */
    private int userAge;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
