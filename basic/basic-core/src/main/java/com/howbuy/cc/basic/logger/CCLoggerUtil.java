package com.howbuy.cc.basic.logger;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.PatternLayout;

/**
 * Created by xinwei.cheng on 2015/8/19.
 */
public class CCLoggerUtil {


    /**
     * 清空其他append 并且设置对应的文件路径的日志对象
     * @param ccLogger
     * @param filePath
     * @return
     */
    public static CCLogger clearAndAddFileLog(CCLogger ccLogger , String filePath){
        if(ccLogger == null){
            return null;
        }

        ccLogger.getOriginalLog().removeAllAppenders();

        ccLogger.getOriginalLog().setAdditivity(false);
        DailyRollingFileAppender appender=new DailyRollingFileAppender();
        appender.setFile(filePath);
        appender.setDatePattern("'.'yyyy-MM-dd");
        PatternLayout layout=new PatternLayout("%-d{yyyy-MM-dd HH:mm:ss}  %m%n");
        appender.setLayout(layout);
        appender.setAppend(true);
        appender.activateOptions();
        ccLogger.getOriginalLog().addAppender(appender);
        return ccLogger;
    }

}
