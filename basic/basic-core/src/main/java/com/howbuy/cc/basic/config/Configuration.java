package com.howbuy.cc.basic.config;

import com.howbuy.cc.basic.CommonConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件获取类
 * Created by xinwei.cheng on 2015/7/6.
 */
@SuppressWarnings("unused")
public final class Configuration {

    private final static Configuration configuration = new Configuration();

    private final static Map<String,String> configMap = new HashMap<>();

    private Configuration(){}

    protected static void init(Properties properties){
        for(Object key : properties.keySet()){
            configMap.put(key.toString() , properties.getProperty(key.toString()));
        }
    }

    protected static void set(String key , String value){
        Configuration.configMap.put(key, value);
    }

    public static String get(String key){
        return Configuration.configMap.get(key);
    }

    public static String getNamespace(){
        return Configuration.configMap.get(CommonConstant.NAMESPACE);
    }

}
