package com.howbuy.server.dubbo.service;

import com.howbuy.service.DubboDemoService;

import java.util.Date;

/**
 * Created by xinwei.cheng on 2015/5/25.
 */

public class DubboDemoServerImpl implements DubboDemoService {


    public Date getDate() {
        System.out.println(this.getClass().getName() + " is invoked");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}