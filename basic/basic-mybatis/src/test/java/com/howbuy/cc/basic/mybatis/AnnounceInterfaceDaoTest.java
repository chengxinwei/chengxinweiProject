package com.howbuy.cc.basic.mybatis;

import com.howbuy.cc.basic.mybatis.dao.interfac.AnnounceInterfaceDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xinwei.cheng on 2015/8/17.
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis-interface.xml")
public class AnnounceInterfaceDaoTest{

    @Autowired
    private AnnounceInterfaceDao announceInterfaceDao;

    @Test
    public void test(){
        System.out.println(announceInterfaceDao);
    }

}
