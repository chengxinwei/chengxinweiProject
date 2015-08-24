package com.howbuy.cc.basic.test.mybatis.dao;

import com.howbuy.cc.basic.cache.constant.CacheConstant;
import com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao;
import com.howbuy.cc.basic.test.model.Aaaa;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/7/20.
 */
@Service
@SuppressWarnings("unused")
public class AaaaCacheDao{

    @Cacheable(value = CacheConstant.REDIS_CACHE_1H , key = "'aaaa' + #id")
    public List<Aaaa> getAaaaCache(int id){
        return null;
    }

    @CacheEvict(value = CacheConstant.REDIS_CACHE_1H , key = "'aaaa' + #id")
    public void clearCache(int id){}


    @Cacheable(value = CacheConstant.REDIS_CACHE_1H , key = "'aaaa' + #aaaa.no")
    public List<Aaaa> set(Aaaa aaaa) {
        List<Aaaa> list = new ArrayList<>();
        list.add(aaaa);
        return list;
    }
}
