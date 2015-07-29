package com.howbuy.cc.basic.logger;

import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public class CCLoggerThreadLocal {

    private final static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String get(){
        return threadLocal.get();
    }

    public static void set(String info){
        threadLocal.set(info);
    }

}
