package com.howbuy.cc.basic.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/8/21.
 */
@Document
public class User {
    @Id
    private Integer userCode;
    private String userName;
    private String type;
    private List<User> userList = new ArrayList<>();

    public User() {
        userList.add(this);
    }


    public User(Integer userCode, String userName, String type) {
        this.userCode = userCode;
        this.userName = userName;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
