package com.howbuy.cc.basic.mongo;

import com.alibaba.fastjson.JSON;
import com.howbuy.cc.basic.mongo.common.BaseTest;
import com.howbuy.cc.basic.mongo.dao.UserDao;
import com.howbuy.cc.basic.mongo.model.User;
import org.junit.Test;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Unit test for simple App.
 */
public class MongoTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    private Integer random = 42;

    @Test
    public void insert()  {
        User user = new User(random , "testName111" , "1");
        userDao.add(user);
    }

    @Test
    public void longSelect()  {
        userDao.testTimeout();
    }

    @Test
    public void execute()  {
        userDao.testTimeout();
    }


    @Test
    public void updateFirst()  {
        userDao.updateFirst(new Query(Criteria.where("userCode").is(random)) , Update.update("userName" , "updateFirstTestName"));
    }


    @Test
    public void updateMulti()  {
        userDao.updateMulti(new Query(Criteria.where("userCode").is(random)), Update.update("userName", "updateMultiTestName"));
    }

    @Test
    public void remove()  {
        userDao.delete(new Query(Criteria.where("userCode").is(random)));
    }

    @Test
    public void findList()  {
        Query query = new Query(Criteria.where("userCode").is(random));
        query.fields().include("_id");
        System.out.println(userDao.findList(query));
    }

    @Test
    public void findOne()  {
        System.out.println(userDao.findOne(new Query(Criteria.where("userCode").is(random))));
    }

    @Test
    public void exists()  {
        System.out.println(userDao.exists(new Query(Criteria.where("userCode").is(random))));
    }

    @Test
    public void save()  {
        User user = new User();
        user.setUserCode(random);
        user.setUserName("saveTestName");
        userDao.save(user);
    }


    @Test
    public void count()  {
        System.out.println(userDao.count(new Query(Criteria.where("userCode").is(random))));
    }

    @Test
    public void selectAlways(){
        while (true){
            try {
                try {
                    this.findOne();
                }catch (Exception e){
                    System.out.println("fail");
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
