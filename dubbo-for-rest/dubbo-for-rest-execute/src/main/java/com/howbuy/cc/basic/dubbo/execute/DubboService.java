package com.howbuy.cc.basic.dubbo.execute;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.howbuy.cc.basic.dubbo.execute.model.Pom;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 主要负责调用动态调用dubbo的服务端
 * Created by xinwei.cheng on 2015/7/10.
 */
public class DubboService {



    public static List<Class> generator(Pom pom) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return DubboClassLoader.loadJar(pom.getFullJarPath());
    }



    public static Object execute(String zookeeperHost, Class<?> clazz, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-for-rest");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(zookeeperHost);

        ReferenceConfig<?> rc = new ReferenceConfig();
        rc.setApplication(application);
        rc.setRegistry(registry);
        rc.setInterface(clazz);
        rc.setProtocol("dubbo");

        Object obj = rc.get();
        return method.invoke(obj, args);
    }

    /**
     * 根据接口 方法 参数调用dubbo 服务
     * @param interfaceClass
     * @param args
     * @return
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object execute(String zookeeperHost, String fullJarPath, String interfaceClass,
                                 String methodName, String[] methodClassStrAry, String... args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, ParserConfigurationException, SAXException, InstantiationException {

        DubboClassLoader.loadJar(fullJarPath);

        Class[] methodClassAry = new Class[methodClassStrAry.length];
        for(int i = 0 ; i < methodClassAry.length ; i ++){
            methodClassAry[i] = Class.forName(methodClassStrAry[i]);
        }

        Class<?> clazz = Class.forName(interfaceClass);
        Method method = clazz.getMethod(methodName , methodClassAry);

        System.out.println("开始调用方法，" + clazz + "." + method.getName());
        return execute(zookeeperHost, clazz, method, matchMethodParams(args, method));

    }

    /**
     * 参数转换成对应的类型
     * @param paramsAry
     * @param method
     * @return
     */
    public static Object[] matchMethodParams(String[] paramsAry, Method method) throws IOException {
        Object[] resultObjectAry = new Object[paramsAry.length];
        Class<?>[] paramsClazzAry = method.getParameterTypes();
        for(int i = 0 ; i < paramsAry.length ; i ++){
            //方法参数
            String params = paramsAry[i];
            Class<?> paramsClazz = paramsClazzAry[i];
            if(paramsClazz.equals(Integer.class)){
                resultObjectAry[i] = Integer.parseInt(params);
            }else if(paramsClazz.equals(String.class)){
                resultObjectAry[i] = params.toString();
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


    public static Class<?> getClass(String className) throws ClassNotFoundException {
        switch (className){
            case "int":
                className ="java.lang.Integer";
                break;
        }
        return Class.forName(className);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(getClass("int"));
    }
}
