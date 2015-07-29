//package com.howbuy.cc.basic.test.service;
//
//import com.howbuy.cc.basic.cache.constant.CacheConstant;
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
//public class TestService {
//
//    @Cacheable(value = CacheConstant.REDIS_CACHE_5S , key = "#key")
//    public Date getDate(int key){
//        return new Date();
//    }
//
//}
