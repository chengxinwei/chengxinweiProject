package com.howbuy.cc.basic.test.model;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/7/20.
 */
@SuppressWarnings("unused")
public class Aaaa implements Serializable{

    private String cate;
    private Integer no;
    private Double score;

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
