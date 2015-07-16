package com.howbuy.cc.basic.dubbo.generator;

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 负责下载完jar包之后 加载jar包中的所有class
 * Created by xinwei.cheng on 2015/7/13.
 */
public class DubboClassLoader {

    private  final static  Logger  logger = Logger.getLogger(DubboClassLoader.class);

    public static List<Class> loadJar(String jarPath) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        jarPath = "jar:file:" + jarPath + "!/";

        URL url = new URL(jarPath);

        JarURLConnection secJarCon = (JarURLConnection)url.openConnection();
        JarFile jarFile = secJarCon.getJarFile();

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url} , ClassLoader.getSystemClassLoader());

        Enumeration<JarEntry> jarEntrys = jarFile.entries();

        List<Class> classList = new ArrayList<>();
        while (jarEntrys.hasMoreElements()) {
            JarEntry entry = jarEntrys.nextElement();
            if(entry.getName().endsWith(".class")){
                String className = entry.getName().replace("/" , ".");
                className = className.substring(0 , className.lastIndexOf("."));

                try{
                    Class<?> clazz = urlClassLoader.loadClass(className);
                    classList.add(clazz);
                }catch (ClassNotFoundException e){
                    logger.error("加载class失败" , e);
                    continue;
                }
                logger.info("加载class成功：" + className);
            }
        }
        Thread.currentThread().setContextClassLoader(urlClassLoader);
        return classList;
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Class> clazzList1 = loadJar("/D:/crm-label-api2Login-model-1.0.jar");
        List<Class> clazzList2 = loadJar("/D:/crm-label-api2Login-model-1.1.jar");
        for(Class<?> clazz : clazzList1){
            if(clazz.getSimpleName().equals("ICustLoginService")){
                System.out.println("class1 ICustLoginService method count " + clazz);
            }
        }
        System.out.println(1);
    }
}
