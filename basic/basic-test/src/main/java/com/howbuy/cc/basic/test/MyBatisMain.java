package com.howbuy.cc.basic.test;

import com.howbuy.cc.basic.spring.SpringBean;
import com.howbuy.cc.basic.test.mybatis.dao.AaaaDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/7/29.
 */
public class MyBatisMain {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("mybatis/application.xml");
        System.out.println(SpringBean.getBean(AaaaDao.class).getAaaa(1));
    }


}
