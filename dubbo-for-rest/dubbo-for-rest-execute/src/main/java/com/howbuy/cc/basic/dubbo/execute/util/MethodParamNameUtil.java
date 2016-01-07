package com.howbuy.cc.basic.dubbo.execute.util;

import com.howbuy.cc.basic.dubbo.execute.model.MethodParamsInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 获取方法的参数信息
 * 包含方法参数名字和方法参数的类型
 * Created by xinwei.cheng on 2015/7/14.
 */
@SuppressWarnings("unused")
public class MethodParamNameUtil {

    public static MethodParamsInfo[] getFieldByClass(Class<?> clazz){
        MethodParamsInfo[] methodParamsInfoAry = new MethodParamsInfo[1];
        MethodParamsInfo methodParamsInfo = new MethodParamsInfo();
        methodParamsInfo.setParamsClass(clazz);
        methodParamsInfo.setParamsName(clazz.getSimpleName());

        List<Field> fieldList = new ArrayList<>();
        while(!clazz.equals(Object.class)) {
            Field[] fieldAry = clazz.getDeclaredFields();
            for (Field field : fieldAry) {
                if (field.getName().equals("serialVersionUID")) {
                    continue;
                }
                fieldList.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        methodParamsInfo.setFieldList(fieldList);
        methodParamsInfoAry[0] = methodParamsInfo;
        return methodParamsInfoAry;
    }


    public static MethodParamsInfo[] getMethodParamsInfo(Class<?> clazz, Method method){
        Class[] methodParamsClasseAry = method.getParameterTypes();

        MethodParamsInfo[] paramNames = new MethodParamsInfo[methodParamsClasseAry.length];

        for (int i = 0; i < paramNames.length; i++) {
            MethodParamsInfo methodParamsInfo = new MethodParamsInfo();
            Class<?> paramsClass = methodParamsClasseAry[i];

            if(!isPrimitive(paramsClass)){
                Class<?> objectClass = paramsClass;
                List<Field> fieldList = new ArrayList<>();
                while(!objectClass.equals(Object.class)) {
                    Field[] fieldAry = objectClass.getDeclaredFields();
                    for (Field field : fieldAry) {
                        if (field.getName().equals("serialVersionUID")) {
                            continue;
                        }
                        fieldList.add(field);
                    }
                    objectClass = objectClass.getSuperclass();
                }
                methodParamsInfo.setFieldList(fieldList);
            }

            methodParamsInfo.setParamsClass(paramsClass);
            paramNames[i] = methodParamsInfo;
        }

        return paramNames;
    }


    private static boolean isPrimitive(Class<?> clazz){
        if(clazz.equals(Integer.class) || clazz.toString().equals("int")){
            return true;
        }else if(clazz.equals(String.class)){
            return true;
        }else if(clazz.equals(Boolean.class) || clazz.toString().equals("boolean")){
            return true;
        }else if(clazz.equals(Character.class) || clazz.toString().equals("char") ){
            return true;
        }else if(clazz.equals(Float.class) || clazz.toString().equals("float") ){
            return true;
        }else if(clazz.equals(Double.class) || clazz.toString().equals("double") ){
            return true;
        }else if(clazz.equals(Short.class) || clazz.toString().equals("short") ){
            return true;
        }else if(clazz.equals(Long.class) || clazz.toString().equals("long") ){
            return true;
        }else if(clazz.equals(Byte.class) || clazz.toString().equals("byte") ){
            return true;
        }
        return false;
    }

//    private static Class<?> getRealType(Class<?> clazz){
//        if(clazz.equals(Integer.class) || clazz.toString().equals("int")){
//            return Integer.class;
//        }else if(clazz.equals(String.class)){
//            return String.class;
//        }else if(clazz.equals(Boolean.class) || clazz.toString().equals("boolean")){
//            return Boolean.class;
//        }else if(clazz.equals(Character.class) || clazz.toString().equals("char") ){
//            return Character.class;
//        }else if(clazz.equals(Float.class) || clazz.toString().equals("float") ){
//            return Float.class;
//        }else if(clazz.equals(Double.class) || clazz.toString().equals("double") ){
//            return Double.class;
//        }else if(clazz.equals(Short.class) || clazz.toString().equals("short") ){
//            return Short.class;
//        }else if(clazz.equals(Long.class) || clazz.toString().equals("long") ){
//            return Long.class;
//        }else if(clazz.equals(Byte.class) || clazz.toString().equals("byte") ){
//            return Byte.class;
//        }
//        return clazz;
//    }
}
