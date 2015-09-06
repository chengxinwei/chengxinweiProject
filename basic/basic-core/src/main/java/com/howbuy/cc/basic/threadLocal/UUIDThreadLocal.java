package com.howbuy.cc.basic.threadLocal;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 当前线程的 UUID
 * Created by xinwei.cheng on 2015/8/5.
 */
public class UUIDThreadLocal {

    private final static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String create(){
        String uuid = UUID.randomUUID().toString();
        threadLocal.set(uuid);
        return uuid;
}

    public static String get(){
        return StringUtils.defaultString(threadLocal.get() , "");
    }

    public static void remove(){
        threadLocal.remove();
    }
}
