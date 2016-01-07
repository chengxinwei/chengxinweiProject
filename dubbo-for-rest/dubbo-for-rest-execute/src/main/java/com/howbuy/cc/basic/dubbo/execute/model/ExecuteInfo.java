package com.howbuy.cc.basic.dubbo.execute.model;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/12/1.
 */
public class ExecuteInfo {

    private Class<?> clazz;
    private Method method;
    private Object[] args;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
