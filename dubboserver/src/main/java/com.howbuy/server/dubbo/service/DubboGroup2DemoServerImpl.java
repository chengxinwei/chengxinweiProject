package com.howbuy.server.dubbo.service;

import com.howbuy.service.DubboDemoService;
import com.howbuy.service.DubboGroupDemoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/5/25.
 */
public class DubboGroup2DemoServerImpl implements DubboGroupDemoService {


    public List getList() {
        System.out.println(this.getClass().getName() + " is invoked");
        List<String> list = new ArrayList();
        list.add("efg");
        return list;
    }
}