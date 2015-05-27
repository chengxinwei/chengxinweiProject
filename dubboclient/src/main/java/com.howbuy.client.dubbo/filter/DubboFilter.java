package com.howbuy.client.dubbo.filter;

import com.alibaba.dubbo.rpc.*;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xinwei.cheng on 2015/5/26.
 */
public class DubboFilter implements Filter{

    public static List<Long> avgList = new Vector<Long>();



    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Date now = new Date();
        Result result = invoker.invoke(invocation);
        long time = new Date().getTime() - now.getTime();
        avgList.add(time);
        return result;
    }



}
