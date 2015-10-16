package com.howbuy.cc.basic.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/8/21.
 */
@Document
public class User implements Serializable{
    @Id
    private Integer userCode;
    private String userName;
    private String type;
    private Integer age;

    public User() {
    }

    public User(Integer userCode, String userName, String type, Integer age) {
        this.userCode = userCode;
        this.userName = userName;
        this.type = type;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
