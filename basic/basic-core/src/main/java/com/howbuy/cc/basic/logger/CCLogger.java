package com.howbuy.cc.basic.logger;

import com.howbuy.cc.basic.constant.CommonConstant;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * logger工具类
 * Created by xinwei.cheng on 2015/7/16.
 */
@SuppressWarnings("unused")
public class CCLogger{

    private Logger logger = null;

    public static CCLogger getLogger(Class<?> clazz){
        CCLogger ccLogger = new CCLogger();
        ccLogger.logger = Logger.getLogger(clazz);
        return ccLogger;
    }

    public void info(String message){
        this.info(null , message);
    }

    public void info(String code , String text , String... messageAry){
        if(logger.isInfoEnabled()){
            logger.info(getDefaultLogHeader(code) + "|" + text + "|" + StringUtils.join(messageAry, "|"));
        }
    }

    public void debug(String message){
        this.debug(null , message);
    }

    public void debug(String code , String text , String... messageAry){
        if(logger.isDebugEnabled()){
            logger.debug(getDefaultLogHeader(code) + "|" + text + "|" + StringUtils.join(messageAry, "|"));
        }
    }

    public void error(String text , Exception e){
        this.error(null, text, e);
    }

    public void error(String code , String text , String... messageAry){
        logger.error(getDefaultLogHeader(code) + "|" + text + "|" + StringUtils.join(messageAry, "|"));
    }

    public void error(String code , String text , Exception e , String... messageAry){
        logger.error(getDefaultLogHeader(code) + "|" + text + "|" + StringUtils.join(messageAry, "|"), e);
    }

    public void warn(String text){
        this.warn(null ,text);
    }

    public void warn(String code , String text , String... messageAry){
        logger.warn(getDefaultLogHeader(code) + "|" + text + "|" + StringUtils.join(messageAry, "|"));
    }


    protected String getDefaultLogHeader(String code){
        String threadLocalCode = CCLoggerThreadLocal.get();
        List<String> ipList = IpUtil.getIp();
        String ip = StringUtils.join(ipList.toArray(new String[ipList.size()]), ",");
        String applicationName = StringUtils.defaultString(Configuration.get(CommonConstant.LOGGER_APPLICATION_NAME) ,  Configuration.getDefaultApplicationName());
        return StringUtils.defaultString(StringUtils.defaultString(code , threadLocalCode) , "") + "|" + ip + "|" + applicationName;
    }

    public Logger getOriginalLog(){
        return logger;
    }
}
