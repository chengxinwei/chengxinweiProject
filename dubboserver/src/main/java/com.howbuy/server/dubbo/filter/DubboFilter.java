package com.howbuy.server.dubbo.filter;

import com.alibaba.dubbo.rpc.*;

import java.util.Date;

/**
 * Created by xinwei.cheng on 2015/5/26.
 */
public class DubboFilter implements Filter{


    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Date now = new Date();
        System.out.println("filter is invoked");
        Result result = invoker.invoke(invocation);

        System.out.println("耗时:" + (new Date().getTime() - now.getTime()));
        return result;
    }
}
