package com.howbuy.cc.basic.sqlite;

import com.howbuy.cc.basic.sqlite.dao.UserDao;
import com.howbuy.cc.basic.sqlite.model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {



        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-mybatis.xml");

        UserDao userDao = classPathXmlApplicationContext.getBean(UserDao.class);
        userDao.create();
        userDao.insert(new User(1 , "user" , "2"));
        System.out.println(userDao.selectList());
    }
}
