//package com.howbuy.cc.basic.test.cache;
//
//import com.howbuy.cc.basic.cache.constant.CacheConstant;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
///**
// * Created by xinwei.cheng on 2015/7/9.
// */
//
//@Service
//public class CacheServiceTest {
//
//    @Cacheable(value = CacheConstant.REDIS_CACHE_5S , key = "#key")
//    public Date getDate_old(int key){
//        return new Date();
//    }
//
//    @CacheEvict(value = CacheConstant.REDIS_CACHE_5S , key = "#key")
//    public Date clearDate_old(int key){
//        return new Date();
//    }
//
//}
