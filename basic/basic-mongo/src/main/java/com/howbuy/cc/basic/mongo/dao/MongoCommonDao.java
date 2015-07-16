package com.howbuy.cc.basic.mongo.dao;

import com.howbuy.cc.basic.mongo.callback.MongoCallBack;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/6/2.
 */
public abstract class MongoCommonDao<T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Class<T> clazz;
    private final String collectionName;


    public MongoCommonDao(){
        clazz = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        collectionName = clazz.getSimpleName();
    }

    public void add(T t){
        mongoTemplate.insert(t , collectionName);
    }

    public WriteResult updateFirst(Query query ,Update update){
        update.set("updateAt" , new Date());
        return mongoTemplate.updateFirst(query , update ,clazz , collectionName);
    }

    public WriteResult updateMulti(Query query ,Update update){
        update.set("updateAt" , new Date());
        return mongoTemplate.updateMulti(query, update , clazz , collectionName);
    }

    public T findOne(Query query){
        return mongoTemplate.findOne(query, clazz , collectionName);
    }

    public List<T> findList(Query query){
        return mongoTemplate.find(query, clazz, collectionName);
    }

    public void delete(Query query){
        mongoTemplate.remove(query , clazz , collectionName);
    }

    public long count(Query query){
        return mongoTemplate.count(query , clazz , collectionName);
    }

    public boolean exists(Query query){
        return mongoTemplate.exists(query , clazz , collectionName);
    }

    public void save(T t){
        mongoTemplate.save(t , collectionName);
    }

    public <E> E excute(MongoCallBack<E> mongoCallBack){
        return mongoCallBack.doCallBack(mongoTemplate , clazz , collectionName);
    }


}
