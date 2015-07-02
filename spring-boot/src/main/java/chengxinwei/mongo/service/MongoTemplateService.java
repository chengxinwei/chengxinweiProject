package chengxinwei.mongo.service;

import chengxinwei.mongo.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by xinwei.cheng on 2015/7/2.
 */
@Component
public class MongoTemplateService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public City find(String name){
        return mongoTemplate.findOne(
                new Query().addCriteria(Criteria.where("name").is(name)),
                City.class
        );

    }

}
