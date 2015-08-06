package com.howbuy.cc.basic.threadLocal;

import java.util.UUID;

/**
 * 当前线程的 UUID
 * Created by xinwei.cheng on 2015/8/5.
 */
public class UUIDThreadLocal {

    private final static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String get(){
        String uuid = threadLocal.get();
        if(uuid == null){
            uuid = UUID.randomUUID().toString();
            threadLocal.set(uuid);
            return uuid.toString();
        }
        return uuid;
    }

}
