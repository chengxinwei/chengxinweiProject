package com.howbuy.cc.base;

import com.howbuy.cc.base.config.Configuration;
import org.junit.Test;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class ConfigurationTest extends BaseTest{


    @Test
    public void test(){
        System.out.println("===>"+Configuration.get("configPath"));
    }

}
