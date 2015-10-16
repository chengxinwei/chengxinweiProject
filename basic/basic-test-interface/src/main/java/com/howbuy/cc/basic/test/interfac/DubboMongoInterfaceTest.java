package com.howbuy.cc.basic.test.interfac;

import com.howbuy.cc.basic.test.model.User;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public interface DubboMongoInterfaceTest {

    public String insert(User user) ;

    public String updateFirstByUserCode(User user)  ;


    public String updateMultiByAge(User user)  ;


    public String removeByUserCode(Integer userCode)  ;


    public List<User> findList(Integer age)  ;


    public User findOneByUserCode(Integer userCode)  ;


    public boolean exists(Integer userCode)  ;


    public String save( User user)  ;


    public Long count(Integer age)  ;

    
}
