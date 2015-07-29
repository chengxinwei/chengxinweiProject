package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.rpc.*;
import com.howbuy.cc.basic.logger.CCLoggerThreadLocal;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public class DubboCodeLoggerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String className = invoker.getInterface().getName();
        CCLoggerThreadLocal.set(className + "." + invocation.getMethodName());
        return invoker.invoke(invocation);
    }
}
