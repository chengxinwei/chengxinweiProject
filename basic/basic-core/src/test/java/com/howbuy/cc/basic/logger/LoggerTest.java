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
    public void testInfo(){
        ccLogger.info("code" );
    }

    @Test
    public void testDebug(){
        ccLogger.debug("code");
    }

    @Test
    public void testWarn(){
        ccLogger.warn("code");
    }

    @Test
    public void testError(){
        ccLogger.error("code", new RuntimeException("test"));
    }

    @Test
    public void changeLogTest(){
        String log = "d:/log.log";
        CCLoggerUtil.clearAndAddFileLog(ccLogger , log);

        ccLogger.info("111111");
    }
}
