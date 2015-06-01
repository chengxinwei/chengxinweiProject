package dao;


import model.HelloKitty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


/**
 * Created by xinwei.cheng on 2015/6/1.
 */
@Repository
public class HelloKittyDao {
    /**
     * 定义集合名称
     */
    private static String HELLOKITTY = "HelloKitty";

    /**
     * 操作MongoDB的对象
     */
    @Autowired
    private MongoTemplate mongoTemplate;


    public void createHelloKitty(HelloKitty hello) {
        mongoTemplate.insert(hello, HELLOKITTY);
    }

    public HelloKitty getHelloKittyByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), HelloKitty.class, HELLOKITTY);
    }
}
