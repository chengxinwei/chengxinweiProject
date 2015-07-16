package com.howbuy.cc.basic.logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

/**
 *
 * Created by xinwei.cheng on 2015/7/16.
 */
public class CCLogger {

    final static public Logger logger = Logger.getLogger(CCLogger.class);

    public static void info(String code , String text , String... messageAry){
        if(logger.isInfoEnabled()){
            String date = new DateTime().toString("yyyyMMddHHmmss");
            logger.info("["+code+"]" + date + "|" + text + StringUtils.join(messageAry , "|"));
        }
    }

    public static void debug(String code , String text , String... messageAry){
        if(logger.isDebugEnabled()){
            String date = new DateTime().toString("yyyyMMddHHmmss");
            logger.debug("[" + code + "]" + date + "|" + text + StringUtils.join(messageAry, "|"));
        }
    }

    public static void error(String code , String text , String... messageAry){
        String date = new DateTime().toString("yyyyMMddHHmmss");
        logger.error("[" + code + "]" + date + "|" + text + StringUtils.join(messageAry, "|"));
    }

    public static void warn(String code , String text , String... messageAry){
        String date = new DateTime().toString("yyyyMMddHHmmss");
        logger.warn("[" + code + "]" + date + "|" + text + StringUtils.join(messageAry, "|"));
    }
}
