package com.howbuy.cc.base.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * spring 加载配置文件
 * Created by xinwei.cheng on 2015/6/1.
 */
@SuppressWarnings("unused")
public class PropertyPlaceHolderResolver extends PropertyPlaceholderConfigurer {

    private static Logger logger = Logger.getLogger(PropertyPlaceHolderResolver.class);

    private final static String GLOBAL = "global.properties";

    private final static String CONFIG_PATH = "configPath";

    private static Properties properties;

    @Override
    protected Properties mergeProperties() throws IOException {
        Properties prop = super.mergeProperties();
        this.loadPropFile(GLOBAL , prop);
        //获取配置文件物理地址
        String configPath = prop.getProperty(CONFIG_PATH);

        if(StringUtils.isNotEmpty(configPath)){
            loadPropFile(configPath , prop);
        }

        PropertyPlaceHolderResolver.properties = prop;
        Configuration.init(prop);
        return properties;
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
            inputStream = new FileInputStream(filePath);
        }

        if(inputStream == null){
            throw new RuntimeException("file " + filePath + " can not find");
        }
        this.loadPropFile(inputStream , superProperties);
    }


    /**
     * 加载某个具体的配置文件
     * @param inputStream 配置文件流
     * @param superProperties 基类
     * @throws IOException
     */
    public void loadPropFile(InputStream inputStream ,  Properties superProperties) throws IOException {
        Properties prop = new Properties();
        try {
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
