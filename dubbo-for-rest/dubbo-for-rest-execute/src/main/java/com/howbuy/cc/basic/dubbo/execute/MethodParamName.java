package com.howbuy.cc.basic.dubbo.execute;

import com.howbuy.cc.basic.dubbo.execute.model.MethodParamsInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 获取方法的参数信息
 * 包含方法参数名字和方法参数的类型
 * Created by xinwei.cheng on 2015/7/14.
 */
@SuppressWarnings("unused")
public class MethodParamName {

    public static MethodParamsInfo[] getMethodParamsInfo(Class<?> clazz, Method method){
        Class[] methodParamsClasseAry = method.getParameterTypes();

        MethodParamsInfo[] paramNames = new MethodParamsInfo[methodParamsClasseAry.length];

//        try {
//            ClassPool pool = ClassPool.getDefault();
//            CtClass cc = pool.get(clazz.getName());
//
//            CtClass[] methodParamsCtClasseAry = new CtClass[methodParamsClasseAry.length];
//            for(int i = 0 ; i < method.getParameterTypes().length ; i ++){
//                methodParamsCtClasseAry[i] = pool.get(methodParamsClasseAry[i].getName());
//            }
//
//            CtMethod cm = cc.getDeclaredMethod(method.getName() , methodParamsCtClasseAry);
//
//            MethodInfo methodInfo = cm.getMethodInfo();
//
//            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//
//            LocalVariableAttribute attr = null;
//            if(codeAttribute != null) {
//                attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
//            }

//            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++) {
                MethodParamsInfo methodParamsInfo = new MethodParamsInfo();
                Class<?> paramsClass = methodParamsClasseAry[i];

                if(!isPrimitive(paramsClass)){
                    Field[] fieldAry = paramsClass.getDeclaredFields();
                    List<Field> fieldList = new ArrayList<>();
                    for(Field field : fieldAry){
                        if(field.getName().equals("serialVersionUID")){
                            continue;
                        }
                        fieldList.add(field);
                    }
                    methodParamsInfo.setFieldList(fieldList);
                }

                methodParamsInfo.setParamsClass(paramsClass);
//                methodParamsInfo.setParamsName(attr == null ? null : attr.variableName(i + pos));
                paramNames[i] = methodParamsInfo;
            }

//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        }
        return paramNames;
    }


    private static boolean isPrimitive(Class<?> clazz){
        if(clazz.equals(Integer.class)){
            return true;
        }else if(clazz.equals(String.class)){
            return true;
        }else if(clazz.equals(Boolean.class)){
            return true;
        }else if(clazz.equals(Character.class)){
            return true;
        }else if(clazz.equals(Float.class)){
            return true;
        }else if(clazz.equals(Double.class)){
            return true;
        }else if(clazz.equals(Short.class)){
            return true;
        }else if(clazz.equals(Long.class)){
            return true;
        }else if(clazz.equals(Byte.class)){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isPrimitive(Object.class));
        System.out.println(isPrimitive(String.class));
    }

}
