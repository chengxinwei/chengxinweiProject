package com.howbuy.cc.basic;

import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.config.PropertyPlaceHolderResolver;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class ConfigurationTest extends BaseTest{


    @Test
    public void test(){
        System.out.println("=一==>"+Configuration.get("configPath"));
    }


    public static void main(String[] args) throws IOException {
        PropertyPlaceHolderResolver propertyPlaceHolderResolver = new PropertyPlaceHolderResolver();
        propertyPlaceHolderResolver.loadPropFileByJar("/" , null , 0);
    }



}
