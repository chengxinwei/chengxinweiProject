package com.howbuy.cc.basic.test.dubbo;

import java.util.List;

import com.howbuy.cc.basic.test.interfac.DubboMongoInterfaceTest;
import com.howbuy.cc.basic.test.model.User;
import com.howbuy.cc.basic.test.mongo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class DubboMongoProviderTest implements DubboMongoInterfaceTest{


    @Autowired
    private UserDao userDao;

    public String insert(User user)  {
        userDao.add(user);
        return "ok";
    }

    public String updateFirstByUserCode(User user)  {
        Query query = new Query(Criteria.where("userCode").is(user.getUserCode()));
        userDao.updateFirst(query, Update.update("userName" , user.getUserName()).set("age" , user.getAge()).set("type" , user.getType()));
        return "ok";
    }


    public String updateMultiByAge(User user)  {
        Query query = new Query(Criteria.where("age").is(user.getAge()));
        userDao.updateMulti(query, Update.update("userName", user.getUserName()).set("type", user.getType()));
        return "ok";
    }


    public String removeByUserCode(Integer userCode)  {
        userDao.delete(new Query(Criteria.where("userCode").is(userCode)));
        return "ok";
    }


    public List<User> findList(Integer age)  {
        Query query = new Query(Criteria.where("age").is(age));
        return userDao.findList(query);
    }


    public User findOneByUserCode(Integer userCode)  {
        return userDao.findOne(new Query(Criteria.where("userCode").is(userCode)));
    }


    public boolean exists(Integer userCode)  {
        return userDao.exists(new Query(Criteria.where("userCode").is(userCode)));
    }


    public String save( User user)  {
        userDao.save(user);
        return "ok";
    }


    public Long count(Integer age)  {
       return userDao.count(new Query(Criteria.where("age").is(age)));
    }

}
