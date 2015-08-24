package com.howbuy.activemq.model;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/8/14.
 */
public class User implements Serializable {

    private int userCode;


    public User(int userCode) {
        this.userCode = userCode;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }
}
