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


    public static void incrHit(String key){
        if(hitCount.get(key) == null){
            hitCount.put(key, new AtomicInteger(1));
        }else{
            hitCount.get(key).incrementAndGet();
        }
    }

    public static void incrMiss(String key){
        if(missCount.get(key) == null){
            missCount.put(key, new AtomicInteger(1));
        }else{
            missCount.get(key).incrementAndGet();
        }
    }

    public static void del(){
        hitCount.clear();
        missCount.clear();
    }


}
