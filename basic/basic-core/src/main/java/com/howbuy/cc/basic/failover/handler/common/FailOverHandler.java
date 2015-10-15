package com.howbuy.cc.basic.failover.handler.common;

import com.howbuy.cc.basic.failover.annation.FailOver;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/9/28.
 */
public interface FailOverHandler {

    public Object handlerFailOver(FailOver failOver , Class<?> targetClass , Method method , Object[] args);

    public void setFailOverValue(FailOver failOver , Class<?> targetClass , Method method , Object[] args , Object object);

}
