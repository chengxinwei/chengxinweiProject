package com.howbuy.cc.basic.dubbo.execute.model;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/14.
 */
public class MethodParamsInfo {

    private String paramsName;
    private Class<?> paramsClass;
    private List<Field> fieldList;

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

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}
