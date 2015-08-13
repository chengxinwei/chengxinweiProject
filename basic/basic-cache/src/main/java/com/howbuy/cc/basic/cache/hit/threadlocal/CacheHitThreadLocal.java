package com.howbuy.cc.basic.cache.hit.threadlocal;

/**
 * Created by xinwei.cheng on 2015/8/13.
 */
public class CacheHitThreadLocal {

    private final static ThreadLocal<Boolean> isHitThreadLocal = new ThreadLocal<>();

    public static boolean isHit(){
        return isHitThreadLocal.get() == null ? false : isHitThreadLocal.get();
    }

    public static void setHit(boolean isHit){
        isHitThreadLocal.set(isHit);
    }

    public static void del(){
        isHitThreadLocal.remove();
    }
}
