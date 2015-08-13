package com.howbuy.cc.basic.logger;

import com.howbuy.cc.basic.common.BaseTest;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public class LoggerTest extends BaseTest{

    CCLogger ccLogger = CCLogger.getLogger(this.getClass());

    @Test
    public void test(){
        ccLogger.info("code" , "message");
    }

    @Test
    public void changeTest(){

        String path = "d:/log.log";

        ccLogger.getOriginalLog().setAdditivity(false);
        DailyRollingFileAppender appender=new DailyRollingFileAppender();
        appender.setFile(path);
        appender.setDatePattern("'.'yyyy-MM-dd");
        PatternLayout layout=new PatternLayout("%-d{yyyy-MM-dd HH:mm:ss}  %m%n");
        appender.setLayout(layout);
        appender.setAppend(true);
        appender.activateOptions();
        ccLogger.getOriginalLog().addAppender(appender);

        ccLogger.info("111111");
    }
}
