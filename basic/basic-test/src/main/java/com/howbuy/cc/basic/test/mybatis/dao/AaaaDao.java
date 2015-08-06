package com.howbuy.cc.basic.test.mybatis.dao;

import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao;
import com.howbuy.cc.basic.test.model.Aaaa;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/7/20.
 */
@Service
@SuppressWarnings("unused")
public class AaaaDao extends MybatisCommonDao<Aaaa> {


    public List<Aaaa> getAaaa(int id){
        Map<String,Object> params = new HashMap<>();
        params.put("id" , id);
        return super.selectList("selectList", params);
    }


    @Cacheable(value = CacheConstant.REDIS_CACHE_1H , key = "'aaaa' + #id")
    public List<Aaaa> getAaaaCache(int id){
       return this.getAaaa(id);
    }


    @CacheEvict(value = CacheConstant.REDIS_CACHE_1H , key = "'aaaa' + #id")
    public void clearCache(int id){}

    public void update(Aaaa a){
        super.update("update" , a);
    }

    public void insert(Aaaa a){
        super.insert("insert" , a);
    }

    public void delete(int id){
        Map<String,Object> params = new HashMap<>();
        params.put("id" , id);
        super.delete("delete" , params);
    }

    @Cacheable(value = CacheConstant.REDIS_CACHE_1H , key = "'aaaa' + #aaaa.no")
    public Aaaa set(Aaaa aaaa) {
        return aaaa;
    }
}
