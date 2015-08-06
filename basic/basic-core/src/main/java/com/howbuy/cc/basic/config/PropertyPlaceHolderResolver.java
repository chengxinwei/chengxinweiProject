package com.howbuy.cc.basic.config;

import com.howbuy.cc.basic.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * spring 加载配置文件
 * Created by xinwei.cheng on 2015/6/1.
 */
@SuppressWarnings("unused")
public class PropertyPlaceHolderResolver extends PropertyPlaceholderConfigurer {

    private static Logger logger = Logger.getLogger(PropertyPlaceHolderResolver.class);

    private final static String PROPERTIES_SUFFIX = ".properties";

    private static Properties properties;

    @Override
    protected Properties mergeProperties() throws IOException {

        Properties prop = super.mergeProperties();

        String defaultDirPath;
        String baseJarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

        logger.info("baseJarPath:" + baseJarPath);
        URL pathUrl = this.getClass().getResource("/");
        logger.info("pathUrl:" + (pathUrl == null ? "" : pathUrl.toString()));

        if(pathUrl == null){
            logger.info("检测到jar包启动");
            baseJarPath = baseJarPath.replace("\\" , "/");
            baseJarPath = baseJarPath.substring( 0 , baseJarPath.lastIndexOf("/") + 1);
            defaultDirPath = baseJarPath + "conf";
            String dirPath = prop.getProperty(CommonConstant.CONFIG_PATH);
            loadPropByDir(dirPath == null ? defaultDirPath : dirPath, prop);
        }else{
            logger.info("检测到源码项目启动");
            this.loadPropFileByPath(prop);
        }

        PropertyPlaceHolderResolver.properties = prop;
        Configuration.init(prop);
        if(StringUtils.isEmpty(prop.getProperty(CommonConstant.DEFAULT_APPLICATION_NAME))){
            throw new RuntimeException(CommonConstant.DEFAULT_APPLICATION_NAME + " is null");
        }
        return properties;
    }


    /**
     * 根据物理文件夹获取配置文件
     * @param dirPath
     * @param superProperties
     * @throws IOException
     */
    public void loadPropByDir(String dirPath , Properties superProperties) throws IOException {
        if(dirPath == null){
            return;
        }
        File dirFile = new File(dirPath);
        this.loadPropByDir(dirFile , superProperties);
    }


    /**
     * 根据物理文件夹获取配置文件
     * @param superProperties
     * @throws IOException
     */
    public void loadPropByDir(File dirFile , Properties superProperties) throws IOException {
        if(!dirFile.isDirectory()){
            logger.warn("配置文件目录"+ dirFile.getAbsolutePath() + "不是一个文件夹，忽略");
            return;
        }
        File[] fileList = dirFile.listFiles();
        for(File file : fileList){
            if(file.isDirectory()){
                this.loadPropByDir(file , superProperties);
            }else if(file.getName().endsWith(PROPERTIES_SUFFIX)){
                this.loadPropFile(new FileInputStream(file) , superProperties , file.getName());
            }
        }
    }

    /**
     * 加载某个具体的配置文件
     * @param filePath 配置文件路径
     * @param superProperties 基类
     * @throws IOException
     */
    public void loadPropFile(String filePath , Properties superProperties) throws IOException {
        InputStream  inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        if(inputStream == null){
            File file = new File(filePath);
            if(file.exists() && !file.isDirectory()) {
                inputStream = new FileInputStream(filePath);
            }else{
                return;
            }
        }

        if(inputStream == null){
            throw new RuntimeException("file " + filePath + " can not find");
        }
        this.loadPropFile(inputStream , superProperties , filePath);
    }


    /**
     * 根据env加载配置文件
     * @param superProperties
     * @throws IOException
     */
    public void loadPropFileByPath(Properties superProperties) throws IOException {
        URL pathUrl = this.getClass().getResource("/");
        String path = URLDecoder.decode(pathUrl.getPath());
        this.loadPropByDir(path , superProperties);
    }


    /**
     * 根据env加载配置文件
     * @param superProperties
     * @throws IOException
     */
    private void loadPropFileByJar(String dirPath ,  Properties superProperties , int dept) throws IOException {
        String baseJarPath = "jar:file:" + this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile() + "!";
        Enumeration<JarEntry> jarEntrys = this.getJarEntry(baseJarPath + dirPath);
        while (jarEntrys.hasMoreElements()) {
            JarEntry entry = jarEntrys.nextElement();
            // 简单的判断路径，如果想做到想Spring，Ant-Style格式的路径匹配需要用到正则。
            String name = entry.getName();
            if(name.split("/").length > 2){
                continue;
            }
            if(!entry.isDirectory() && name.endsWith(PROPERTIES_SUFFIX)){
                this.loadPropFile(name , superProperties);
            }
        }
    }


    private Enumeration<JarEntry> getJarEntry(String jarFilePath) throws IOException {
        logger.info("urlStr:"+jarFilePath);
        URL url = new URL(jarFilePath);
        JarURLConnection secJarCon = (JarURLConnection)
                url.openConnection();
        JarFile jarFile = secJarCon.getJarFile();
        return jarFile.entries();
    }


    /**
     * 加载某个具体的配置文件
     * @param inputStream 配置文件流
     * @param superProperties 基类
     * @throws IOException
     */
    public void loadPropFile(InputStream inputStream ,  Properties superProperties , String fileName) throws IOException {
        Properties prop = new Properties();
        try {
            logger.info("加载配置文件:" + fileName);
            prop.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage() , e);
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
        for(String propName : prop.stringPropertyNames()){
            String value = prop.getProperty(propName);
            superProperties.put(propName, value == null ? null : value.trim());
            logger.info(propName + ":" + value);
        }
    }

}
