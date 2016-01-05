package com.howbuy.cc.basic.mongo;

import com.alibaba.fastjson.JSON;
import com.howbuy.cc.basic.mongo.common.BaseTest;
import com.howbuy.cc.basic.mongo.dao.UserDao;
import com.howbuy.cc.basic.mongo.model.User;
import org.junit.Test;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class MongoTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    private Integer random = 10011;

    @Test
    public void insert()  {
        User user = new User(random , "testVersion" , "1" , 0);
        user.setDate(new Date());
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
    public void updateByVersion()  {
        long start = System.currentTimeMillis();
        List<Thread> threadList = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++) {
            final int finalI = i;
            threadList.add(new Thread(){
                @Override
                public void run(){
                    while(true) {
                        Query query = new Query(Criteria.where("userCode").is(random));
                        try {
                            User user = userDao.findOne(query);
                            if(user == null || user.getAge() == null){
                                break;
                            }
                            user.setAge(user.getAge() + 1);
                            query.addCriteria(Criteria.where("version").is(user.getVersion()));
                            userDao.updateFirst(query, Update.update("age", user.getAge()));
                            break;
                        }catch(OptimisticLockingFailureException e){
                            System.out.println("重试" + finalI);
                        }
                    }
                }
            });
        }
        for(Thread t : threadList){
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void updateByInc()  {
        long start = System.currentTimeMillis();
        List<Thread> threadList = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++) {
            threadList.add(new Thread(){
                @Override
                public void run(){
                    Query query = new Query(Criteria.where("userCode").is(random));
                    userDao.updateFirst(query, new Update().inc("age" , 1));
                }
            });
        }
        for(Thread t : threadList){
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void updateFirst()  {
        Query query = new Query(Criteria.where("userCode").is(random));
        userDao.updateFirst(query, Update.update("userName" , "testUpdateFirst"));
    }


    @Test
    public void updateMulti()  {
        userDao.updateMulti(new Query(Criteria.where("userCode").is(random)), Update.update("userName", "updateMultiTestName"));
    }

    @Test
    public void findList()  {
        Query query = new Query(Criteria.where("userCode").is(random));
        query.fields().include("_id");
        System.out.println(userDao.findList(query));
    }

    @Test
    public void findByDate()  {
        System.out.println(userDao.findOne(new Query(Criteria.where("date").lt(new Date()))));
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
        User user = userDao.findOne(new Query().addCriteria(Criteria.where("userCode").is(random)));
        if(user != null){
            user.setAge(1);
            user.setUserCode(random);
            user.setUserName("saveTest123Name1");
            userDao.save(user);
        }
    }


    @Test
    public void count()  {
        System.out.println(userDao.count(new Query(Criteria.where("userCode").is(random))));
    }

    @Test
    public void selectAlways(){
        for(int i = 0 ;i < 5; i ++){
            try {
                try {
                    this.findOne();
                }catch (Exception e){
                    System.out.println("fail");
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void remove()  {
        userDao.delete(new Query(Criteria.where("userCode").is(random)));
    }


    
}
