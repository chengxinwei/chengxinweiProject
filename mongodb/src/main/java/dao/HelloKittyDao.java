package dao;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import model.HelloKitty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xinwei.cheng on 2015/6/1.
 */
@Repository
public class HelloKittyDao {
    /**
     * 定义集合名称
     */
    private static String HELLOKITTY = "HelloKittyList";

    /**
     * 操作MongoDB的对象
     */
    @Autowired
    private MongoTemplate mongoTemplate;


    public void createHelloKitty(HelloKitty hello) {
        mongoTemplate.insert(hello);
    }

    public HelloKitty updateHelloKitty(HelloKitty hello) {
        Update update = Update.update("name" , hello.getName());
        return mongoTemplate.findAndModify(new Query(Criteria.where("id").is(hello.getId())) , update ,HelloKitty.class);
    }

    public HelloKitty getHelloKittyByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), HelloKitty.class);
    }

    public HelloKitty getHelloKittyByNameWithCollection(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), HelloKitty.class , HELLOKITTY);
    }

    public List<HelloKitty> get(){
        return mongoTemplate.findAll(HelloKitty.class , HELLOKITTY);
    }
}
