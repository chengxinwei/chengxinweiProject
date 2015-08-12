package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.howbuy.cc.basic.threadLocal.CCLoggerThreadLocal;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public class DubboCodeLoggerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String className = invoker.getInterface().getSimpleName();
        CCLoggerThreadLocal.set(className + "." + invocation.getMethodName());
        Result result = invoker.invoke(invocation);
        return result;
    }
}
