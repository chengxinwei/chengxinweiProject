package com.howbuy.model;

/**
 * Created by xinwei.cheng on 2015/5/27.
 */
public class User {

    private Integer userCode;
    private String userName;

    public User() {
    }

    public User(Integer userCode, String userName) {
        this.userCode = userCode;
        this.userName = userName;
    }

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
