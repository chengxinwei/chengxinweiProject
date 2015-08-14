package com.chengxinwei.datas.writer.writer;

import com.chengxinwei.datas.writer.writer.common.DataWriter;
import com.howbuy.cc.basic.config.Configuration;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/10.
 */
@Service
public class MongoWriter extends DataWriter {

    @Override
    public void write(String tableName , List<Map<String,Object>> list) {
        System.out.println(tableName  + list);
        //相当于
        Mongo mongo =new Mongo(Configuration.get("mongo.server.ip"), Configuration.getInt("mongo.server.port"));
        DB db=mongo.getDB("myMongo");
        DBCollection collection=db.getCollection(tableName);

        //在mongodb中没有行的概念，而是指文档
        BasicDBObject document=new BasicDBObject();
        for(Map<String,Object> map : list) {
            document.putAll(map);
            collection.insert(document);
        }



    }

}
