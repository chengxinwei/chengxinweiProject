package com.howbuy.cc.basic.dubbo;

import com.howbuy.cc.basic.logger.CCLogger;

import java.util.Date;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public class DubboProviderTest implements DubboInterfaceTest{

    CCLogger ccLogger = CCLogger.getLogger(this.getClass());

    @Override
    public Date getDate() {
        ccLogger.info("测试日志功能");
        return new Date();
    }

    @Override
    public void exception() {
        throw new NullPointerException("testException");
    }
}
