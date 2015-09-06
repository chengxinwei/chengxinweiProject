package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.rpc.*;
import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.threadLocal.UUIDThreadLocal;

/**
 * Created by xinwei.cheng on 2015/5/27.
 */
public class DubboUUIDLoggerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        UUIDThreadLocal.create();
        Result obj = invoker.invoke(invocation);
        UUIDThreadLocal.remove();
        return obj;
    }
}