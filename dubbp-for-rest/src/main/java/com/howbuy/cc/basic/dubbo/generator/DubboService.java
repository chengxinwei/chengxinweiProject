package com.howbuy.cc.basic.dubbo.generator;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.dubbo.generator.model.Pom;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javassist.ClassPath;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 主要负责调用动态调用dubbo的服务端
 * Created by xinwei.cheng on 2015/7/10.
 */
public class DubboService {

    private static Map<Pom,List<Class>> pomIdClazzList = new HashMap<>();


    public static List<Class> generator(Pom pom) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //加载类
        List<Class> classList = DubboClassLoader.loadJar(pom.getFullJarPath());
        pom.setClassLoader(Thread.currentThread().getContextClassLoader());
        pomIdClazzList.put(pom , classList);
        return classList;
    }



    public static Object excute(Class<?> clazz , Method method , Object[] args) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-for-rest");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(Configuration.get("zookeeper.ip_port"));


        ReferenceConfig<?> rc = new ReferenceConfig();
        rc.setApplication(application);
        rc.setRegistry(registry);
        rc.setInterface(clazz);
        rc.setProtocol("dubbo");

        try {
            Object obj = rc.get();
            return method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    /**
     * 根据接口 方法 参数调用dubbo 服务
     * @param interfaceClass
     * @param methodName
     * @param args
     * @return
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object excute(String artifactId , String groupId , String interfaceClass , String methodName , Class[] methodParamsClassAry , String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        Pom pom = new Pom();
        pom.setGroupId(groupId);
        pom.setArtifactId(artifactId);

        for(Map.Entry<Pom,List<Class>> entry : pomIdClazzList.entrySet()){
            Pom entryPom = entry.getKey();
            if(entryPom.getArtifactId().equals(artifactId) && entryPom.getGroupId().equals(groupId)){

                for(Class<?> clazz : entry.getValue()){
                    if(clazz.getName().equals(interfaceClass)){
                        Method method = clazz.getMethod(methodName , methodParamsClassAry);
                        Object[] methodArgs  = matchMethodParams(args , method);
                        Thread.currentThread().setContextClassLoader(entryPom.getClassLoader());
                        return excute(clazz , method , methodArgs);
                    }
                }
            }
        }


        throw  new ClassNotFoundException("class "+ interfaceClass + " is not found");

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

}
