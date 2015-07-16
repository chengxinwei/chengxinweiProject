package com.howbuy.cc.basic.dubbo.generator.model;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/13.
 */
@SuppressWarnings("unused")
public class InterfaceInfo {

    private Class<?> clazz;
    private MethodParamsInfo[] methodParamsInfoAry;
    private Method method;
    private String requestUrl;
    private String fullMethodName;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public MethodParamsInfo[] getMethodParamsInfoAry() {
        return methodParamsInfoAry;
    }

    public void setMethodParamsInfoAry(MethodParamsInfo[] methodParamsInfoAry) {
        this.methodParamsInfoAry = methodParamsInfoAry;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getFullMethodName() {
        if(method == null){
            return null;
        }
        String methodParamsStr = "";
        if(methodParamsInfoAry != null ) {
            List<String> methodParamsNameList = new ArrayList<>();
            for (MethodParamsInfo methodParamsInfo : methodParamsInfoAry) {
                Class<?> paramClass = methodParamsInfo.getParamsClass();
                String paramName = methodParamsInfo.getParamsName();
                methodParamsNameList.add(paramClass.getSimpleName() + " " + (paramName == null ? "unkown" : paramName));
            }
            methodParamsStr = StringUtils.join(methodParamsNameList.toArray(new String[0]) , ",");
        }
        String methodName = method.getName();
        methodName = methodName + " ( " + methodParamsStr + " ) ";
        return methodName;
    }

}
