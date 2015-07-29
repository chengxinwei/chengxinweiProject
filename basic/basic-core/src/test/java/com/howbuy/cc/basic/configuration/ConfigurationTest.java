package com.howbuy.cc.basic.configuration;

import com.howbuy.cc.basic.common.BaseTest;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.spring.SpringBean;
import org.junit.Test;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class ConfigurationTest extends BaseTest {

    @Test
    public void test(){
        System.out.println(Configuration.get("default.application.name"));

        System.out.println(SpringBean.getBean(ConfigurationValueTest.class).getValue());
    }

}
