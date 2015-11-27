package com.howbuy.cc.basic.mongo.dao;

import com.howbuy.cc.basic.model.Page;
import com.howbuy.cc.basic.mongo.callback.MongoCallBack;
import com.howbuy.cc.basic.mongo.namespace.MongoOperationSource;
import com.howbuy.cc.basic.spring.SpringBean;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/6/2.
 */
@SuppressWarnings("unused")
public abstract class MongoCommonDao<T> {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoOperationSource mongoOperationSource;

    private final Class<T> clazz;

    public MongoCommonDao(){
        ParameterizedType parameterizedType = null;
        Class _clazz =  this.getClass();
        while (true) {
            Type type = _clazz.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType)type;
                break;
            }
            _clazz = _clazz.getSuperclass();
        }
        clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    public void add(final T t){
        mongoTemplate.insert(t);
    }

    public WriteResult updateFirst(final Query query ,final Update update){
        update.set("updateAt", new Date());
        return mongoTemplate.updateFirst(query, update, clazz);
    }

    public WriteResult updateFirst(final Query query ,final Update update , final Sort sort){
        update.set("updateAt", new Date());
        return mongoTemplate.updateFirst(query.with(sort), update, clazz);
    }

    public WriteResult updateMulti(final Query query ,final Update update){
        update.set("updateAt", new Date());
        return mongoTemplate.updateMulti(query, update, clazz);
    }

    public T findOne(final Query query){
        return mongoTemplate.findOne(query, clazz);
    }

    public List<T> findList(final Query query){
        return mongoTemplate.find(query, clazz);
    }

    public void delete(final Query query){
        mongoTemplate.remove(query, clazz);
    }

    public long count(final Query query){
        return mongoTemplate.count(query , clazz);
    }

    public void save(final T t){
        mongoTemplate.save(t);
    }

    public boolean exists(final Query query){
        return mongoTemplate.exists(query, clazz);
    }

    public Page<T> page(final Query query , final int pageNo , final int pageSize , final Sort sort){
        long count = this.count(query);
        Page page = new Page(pageSize , pageNo , count);
        if(count == 0 ){
            page.setPageList(new ArrayList<T>());
            return page;
        }
        page.setPageList(mongoTemplate.find(query.with(sort).skip(page.getBeginNum()).limit(pageSize), clazz));
        return page;
    }

    public <E> E execute(MongoCallBack<E> mongoCallBack){
        MongoCommonDao mongoCommonDao = SpringBean.getBean(this.getClass());
        return (E)mongoCommonDao.doExecute(mongoCallBack);
    }

    public <E> E doExecute(MongoCallBack<E> mongoCallBack){
        return mongoCallBack.doCallBack(mongoTemplate , clazz);
    }

}
