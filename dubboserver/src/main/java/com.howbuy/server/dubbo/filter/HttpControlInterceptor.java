package com.howbuy.server.dubbo.filter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/5/27.
 */
public class HttpControlInterceptor implements WriterInterceptor {

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        System.out.println(this.getClass().getName() + "is invoked");
        context.proceed();
    }
}