package com.howbuy.cc.basic.cache.util;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */


/**
 * <p>
 * <font color="red">依赖javassit</font>的工具类，获取方法的参数名
 * </p>
 *
 * @author dixingxing
 * @date Apr 20, 2012
 */
public class MethodNameUtil {

    /**
     * <p>
     * 获取方法参数名称
     * </p>
     *
     * @param cm
     * @return
     */
    protected static String[] getMethodParamNames(CtMethod cm) throws NotFoundException {
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return new String[0];
        }

        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = attr.variableName(i + pos);
        }
        return paramNames;
    }

    /**
     * 获取方法参数名称，按给定的参数类型匹配方法
     *
     * @param clazz
     * @param method
     * @param paramTypes
     * @return
     */
    public static String[] getMethodParamNames(Class<?> clazz, String method,
                                               Class<?>... paramTypes) throws NotFoundException {

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(clazz.getName());
        String[] paramTypeNames = new String[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++)
            paramTypeNames[i] = paramTypes[i].getName();

        CtMethod cm = cc.getDeclaredMethod(method, pool.get(paramTypeNames));
        return getMethodParamNames(cm);
    }

    /**
     * 获取方法参数名称，匹配同名的某一个方法
     * @param clazz
     * @param method
     * @return
     * @throws NotFoundException  如果类或者方法不存在
     */
    public static String[] getMethodParamNames(Class<?> clazz, Method method) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(clazz.getName());
        CtMethod cm = cc.getDeclaredMethod(method.getName());
        return getMethodParamNames(cm);
    }

}

