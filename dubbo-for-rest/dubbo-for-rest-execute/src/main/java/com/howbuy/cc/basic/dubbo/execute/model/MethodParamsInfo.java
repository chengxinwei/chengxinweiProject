package com.howbuy.cc.basic.dubbo.execute.model;

/**
 * Created by xinwei.cheng on 2015/7/14.
 */
public class MethodParamsInfo {

    private String paramsName;
    private Class<?> paramsClass;

    public String getParamsName() {
        return paramsName;
    }

    public void setParamsName(String paramsName) {
        this.paramsName = paramsName;
    }

    public Class<?> getParamsClass() {
        return paramsClass;
    }

    public void setParamsClass(Class<?> paramsClass) {
        this.paramsClass = paramsClass;
    }
}
