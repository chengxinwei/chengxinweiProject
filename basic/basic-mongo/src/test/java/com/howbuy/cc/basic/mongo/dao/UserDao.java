package com.howbuy.cc.basic.mongo.dao;

import com.howbuy.cc.basic.mongo.callback.MongoCallBack;
import com.howbuy.cc.basic.mongo.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by xinwei.cheng on 2015/8/21.
 */
@Repository
public class UserDao extends MongoCommonDao<User>{
    
    public void testTimeout(){
        super.execute(new MongoCallBack<Void>() {
            @Override
            public Void doCallBack(MongoTemplate mongoTemplate, Class<?> clazz) {
                mongoTemplate.count(new Query(Criteria.where("userCode").is(41)) , clazz);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                }
                return null;
            }
        });
    }
    
}
