package com.howbuy.cc.basic.mybatis;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-mybatis.xml" ,
        "classpath:basic/spring/base-spring.xml" ,
        "classpath:basic/spring/mybatis/spring-mybatis.xml"})
public abstract class BaseTest {



}
