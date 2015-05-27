package com.howbuy.server.dubbo.service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.howbuy.model.User;
import com.howbuy.service.DubboHttpDemoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by xinwei.cheng on 2015/5/25.
 */

@Path("user")
@Produces({ContentType.APPLICATION_JSON_UTF_8})
public class DubboHttpDemoServerImpl implements DubboHttpDemoService {


    @GET
    @Path("getUser")
    public User getUser() {
       return new User(8888 , "程欣伟");
    }


}