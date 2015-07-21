package com.howbuy.cc.basic.logger;

import com.howbuy.cc.basic.CommonConstant;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.nutz.lang.Strings;

import java.util.List;

/**
 * logger工具类
 * Created by xinwei.cheng on 2015/7/16.
 */
@SuppressWarnings("unused")
public class CCLogger {

    final static public Logger logger = Logger.getLogger(CCLogger.class);

    public static void info(String code , String text , String... messageAry){
        if(logger.isInfoEnabled()){
            logger.info(CCLogger.getDefaultLogHeader(code) + "|" + text + StringUtils.join(messageAry , "|"));
        }
    }

    public static void debug(String code , String text , String... messageAry){
        if(logger.isDebugEnabled()){
            logger.debug(CCLogger.getDefaultLogHeader(code) + "|" + text + StringUtils.join(messageAry, "|"));
        }
    }

    public static void error(String code , String text , String... messageAry){
        logger.error(CCLogger.getDefaultLogHeader(code) + "|" + text + StringUtils.join(messageAry, "|"));
    }

    public static void error(String code , String text , Exception e , String... messageAry){
        logger.error(CCLogger.getDefaultLogHeader(code) + "|" + text + StringUtils.join(messageAry, "|") , e);
    }

    public static void warn(String code , String text , String... messageAry){
        logger.warn(CCLogger.getDefaultLogHeader(code) + "|" + text + StringUtils.join(messageAry, "|"));
    }


    protected static String getDefaultLogHeader(String code){
        List<String> ipList = IpUtil.getIp();
        String ip = StringUtils.join(ipList.toArray(new String[ipList.size()]), ",");
        String date = new DateTime().toString("yyyyMMddHHmmss");
        String applicationName = Configuration.get(CommonConstant.LOGGER_APPLICATION_NAME) == null ? Configuration.getDefaultApplicationName() : Configuration.get(CommonConstant.LOGGER_APPLICATION_NAME);
        return "["+code+"]" + "|" + date + "|" + ip + "|" + applicationName;
    }
}
