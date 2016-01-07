package com.howbuy.cc.basic.dubbo.execute.service.common;

import com.howbuy.cc.basic.dubbo.execute.model.ExecuteInfo;
import com.howbuy.cc.basic.dubbo.execute.service.dubbo.DubboExecute;
import com.howbuy.cc.basic.dubbo.execute.util.ClassLoaderUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by xinwei.cheng on 2015/11/30.
 */
public class ExecuteService {


//    public static Object execute(String zookeeperHost, String fullJarPath, String interfaceClass,
//                                  String methodName, String[] methodClassStrAry, String... args) throws Exception {
//        ExecuteInfo executeInfo = prepareExecute(fullJarPath, interfaceClass, methodName, methodClassStrAry, args);
//        return DubboExecute.execute(zookeeperHost, executeInfo.getClazz(), executeInfo.getMethod(), executeInfo.getArgs());
//    }


    public static ExecuteInfo prepareExecute( String fullJarPath, String interfaceClass,
                                       String methodName, String[] methodClassStrAry, boolean isDubbo , String... args) throws Exception {

        ExecuteInfo executeInfo = new ExecuteInfo();

        //加载jar包
        ClassLoaderUtil.loadJar(fullJarPath, true);
        //获取方法的参数类型
        Class[] methodClassAry = new Class[methodClassStrAry.length];
        for(int i = 0 ; i < methodClassAry.length ; i ++){
            methodClassAry[i] = Class.forName(methodClassStrAry[i]);
        }
        Class<?> clazz = Class.forName(interfaceClass);
        executeInfo.setClazz(clazz);

        if(isDubbo) {
            Method method = clazz.getMethod(methodName, methodClassAry);
            System.out.println("开始调用方法，" + clazz + "." + method.getName());
            //解析方法的类型
            Object[] argsObject = matchMethodParams(args, method);
            executeInfo.setMethod(method);
            executeInfo.setArgs(argsObject);
        }
        return executeInfo;
    }

    /**
     * 参数转换成对应的类型
     * @param paramsAry
     * @param method
     * @return
     */
    private static Object[] matchMethodParams(String[] paramsAry, Method method) throws IOException {
        Object[] resultObjectAry = new Object[paramsAry.length];
        Class<?>[] paramsClazzAry = method.getParameterTypes();
        for(int i = 0 ; i < paramsAry.length ; i ++){
            //方法参数
            String params = paramsAry[i];
            Class<?> paramsClazz = paramsClazzAry[i];
            if(paramsClazz.equals(Integer.class)){
                resultObjectAry[i] = Integer.parseInt(params);
            }else if(paramsClazz.equals(String.class)){
                resultObjectAry[i] = params;
            }else if(paramsClazz.equals(Double.class)){
                resultObjectAry[i] = Double.parseDouble(params);
            }else if(paramsClazz.equals(Float.class)){
                resultObjectAry[i] = Float.parseFloat(params);
            }else if(paramsClazz.equals(Boolean.class)){
                resultObjectAry[i] = Boolean.parseBoolean(params);
            }else if(paramsClazz.equals(Long.class)){
                resultObjectAry[i] = Long.parseLong(params);
            }else if(paramsClazz.equals(Short.class)){
                resultObjectAry[i] = Short.parseShort(params);
            }else if(paramsClazz.equals(Date.class)){
                try {
                    resultObjectAry[i] = DateTime.parse(params, DateTimeFormat.forPattern("yyyy-MM-dd")).toDate();
                }catch(Exception e){
                    resultObjectAry[i] = DateTime.parse(params, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
                }
            }else{
                try {
                    resultObjectAry[i] = new ObjectMapper().readValue(params, paramsClazz);
                } catch (IOException e) {
                    throw new IOException("请求参数转换错误"  , e);
                }
            }
        }
        return resultObjectAry;
    }



}
