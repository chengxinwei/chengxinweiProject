package com.howbuy.server.dubbo.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * Created by xinwei.cheng on 2015/5/27.
 */
public class HttpControlFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext req, ContainerResponseContext res) {

        System.out.println(this.getClass().getName() + "is invoked");

    }
}