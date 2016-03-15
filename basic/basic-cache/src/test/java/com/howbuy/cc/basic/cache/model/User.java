package com.howbuy.cc.basic.cache.model;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/7/9.
 */
public class User implements Serializable {

    private Integer id;
    private String name;
    public Integer getId() {
        return id;
    }

    public byte[] byteAry;

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getByteAry() {
        return byteAry;
    }

    public void setByteAry(byte[] byteAry) {
        this.byteAry = byteAry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
