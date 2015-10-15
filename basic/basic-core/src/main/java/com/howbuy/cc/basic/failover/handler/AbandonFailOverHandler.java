package com.howbuy.cc.basic.failover.handler;

import com.howbuy.cc.basic.failover.annation.FailOver;
import com.howbuy.cc.basic.failover.handler.common.FailOverHandler;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/9/28.
 */
public class AbandonFailOverHandler implements FailOverHandler {

    @Override
    public Object handlerFailOver(FailOver failOver , Class<?> targetClass , Method method , Object[] args) {
        return null;
    }

    @Override
    public void setFailOverValue(FailOver failOver, Class<?> targetClass, Method method, Object[] args, Object object) {

    }
}
