package com.howbuy.cc.basic.cache.hit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xinwei.cheng on 2015/8/13.
 */
public final class HitCount {

    public final static Map<String,AtomicInteger> hitCount = new ConcurrentHashMap<>();

    public final static Map<String,AtomicInteger> missCount = new ConcurrentHashMap<>();


    public static void incrHit(String classMethodName){
        if(hitCount.get(classMethodName) == null){
            hitCount.put(classMethodName , new AtomicInteger(1));
        }else{
            hitCount.get(classMethodName).incrementAndGet();
        }
    }

    public static void incrMiss(String classMethodName){
        if(missCount.get(classMethodName) == null){
            missCount.put(classMethodName , new AtomicInteger(1));
        }else{
            missCount.get(classMethodName).incrementAndGet();
        }
    }

    public static void del(){
        hitCount.clear();
        missCount.clear();
    }


}
