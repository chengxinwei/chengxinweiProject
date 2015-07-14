package com.howbuy.cc.basic.mongo.callback;

import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
public interface MongoCallBack<E> {

    public E doCallBack(MongoTemplate mongoTemplate , Class<?> clazz , String collectionName);

}
