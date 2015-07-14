package com.howbuy.cc.basic.dubbo.generator;

import com.howbuy.cc.basic.dubbo.generator.model.MethodParamsInfo;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 获取方法的参数信息
 * 包含方法参数名字和方法参数的类型
 * Created by xinwei.cheng on 2015/7/14.
 */
@SuppressWarnings("unused")
public class MethodParamName {

    public static MethodParamsInfo[] getMethodParamsName(Class<?> clazz , Method method){
        Class[] methodParamsClasseAry = method.getParameterTypes();

        MethodParamsInfo[] paramNames = new MethodParamsInfo[methodParamsClasseAry.length];

        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(clazz.getName());

            CtClass[] methodParamsCtClasseAry = new CtClass[methodParamsClasseAry.length];
            for(int i = 0 ; i < method.getParameterTypes().length ; i ++){
                methodParamsCtClasseAry[i] = pool.get(methodParamsClasseAry[i].getName());
            }

            CtMethod cm = cc.getDeclaredMethod(method.getName() , methodParamsCtClasseAry);

            MethodInfo methodInfo = cm.getMethodInfo();

            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();

            LocalVariableAttribute attr = null;
            if(codeAttribute != null) {
                attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            }

            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++) {
                MethodParamsInfo methodParamsInfo = new MethodParamsInfo();
                methodParamsInfo.setParamsClass(methodParamsClasseAry[i]);
                methodParamsInfo.setParamsName(attr == null ? null : attr.variableName(i + pos));
                paramNames[i] = methodParamsInfo;
            }

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return paramNames;
    }

    public static void main(String[] args) throws NoSuchMethodException, IOException, ClassNotFoundException {
//        Class<?> clazz = JarGenerator.class;
        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("com.howbuy.cc.basic.mvc.DubboController");
//        Class<?> clazz = Class.forName("com.howbuy.cc.basic.dubbo.generator.JarGenerator");
        Method method = clazz.getMethod("dubboExcute", String.class , String.class);
        System.out.println(new ObjectMapper().writeValueAsString(getMethodParamsName(clazz, method)));
    }
}
