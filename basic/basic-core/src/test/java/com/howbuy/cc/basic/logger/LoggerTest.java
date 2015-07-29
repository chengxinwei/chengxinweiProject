package com.howbuy.cc.basic.logger;

import com.howbuy.cc.basic.common.BaseTest;
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

}
