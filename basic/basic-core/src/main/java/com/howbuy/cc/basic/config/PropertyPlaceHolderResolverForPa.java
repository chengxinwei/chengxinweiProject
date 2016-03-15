package com.howbuy.cc.basic.config;

import com.howbuy.cc.basic.constant.CommonConstant;
import com.howbuy.config.client.ConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * spring 加载配置文件
 * Created by xinwei.cheng on 2015/6/1.
 */
@SuppressWarnings("unused")
public class PropertyPlaceHolderResolverForPa extends PropertyPlaceholderConfigurer {

    private Logger logger = Logger.getLogger(PropertyPlaceHolderResolverForPa.class);


    @Override
    protected Properties mergeProperties() throws IOException {
        Properties prop = super.mergeProperties();

        ConfigUtil u2 = new ConfigUtil();
        List<String> fileAll = u2.getAllFile();
        for(String str : fileAll){
            logger.info("加载配置文件:" + str);
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(str));
                Properties properties = new Properties();
                properties.load(fileInputStream);

                for(String propName : properties.stringPropertyNames()){
                    String value = properties.getProperty(propName);
                    value = value == null ? null : new String(value.getBytes("ISO-8859-1") , "UTF-8").trim();
                    if(value != null) {
                        prop.put(propName, value);
                        logger.info(propName + ":" + value);
                    }
                }
            }catch (Exception e){
                logger.error(e.getMessage() , e);
            }
        }
        Configuration.init(prop);
        if(StringUtils.isEmpty(prop.getProperty(CommonConstant.DEFAULT_APPLICATION_NAME))){
            throw new RuntimeException(CommonConstant.DEFAULT_APPLICATION_NAME + " is null");
        }
        return prop;
    }

}
