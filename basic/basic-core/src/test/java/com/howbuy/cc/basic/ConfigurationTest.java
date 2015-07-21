package com.howbuy.cc.basic;

import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.config.PropertyPlaceHolderResolver;
import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.spring.SpringBean;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class ConfigurationTest extends BaseTest{



    @Test
    public void test(){
        System.out.println("=一==>"+Configuration.get("default.application.name"));
        CCLogger.debug("code" , "aaa");
        CCLogger.info("code" , "aaa");
        CCLogger.warn("code", "aaa");
        CCLogger.error("code" , "aaa" , new RuntimeException("aaaaaaaaaaaaaaa"));
        System.out.println(SpringBean.getBean(ConfigurationValueTest.class).getValue());
    }

}
