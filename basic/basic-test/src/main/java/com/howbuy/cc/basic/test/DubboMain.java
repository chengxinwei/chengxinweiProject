package com.howbuy.cc.basic.test;

import com.alibaba.dubbo.container.Main;
import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import javassist.NotFoundException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by xinwei.cheng on 2015/7/29.
 */
public class DubboMain {

    public static void main(String[] args) throws NotFoundException {
        new ClassPathXmlApplicationContext("application.xml");
        Main.main(args);
    }

}
