package com.howbuy.cc.basic.cache.namespace;

/**
 * Created by xinwei.cheng on 2015/8/13.
 */
public class CacheHitOperationSource {

    private String hitLogPath;
    private int hitLogTime = 1000 * 60 * 60;

    public String getHitLogPath() {
        return hitLogPath;
    }

    public void setHitLogPath(String hitLogPath) {
        this.hitLogPath = hitLogPath;
    }

    public int getHitLogTime() {
        return hitLogTime;
    }

    public void setHitLogTime(int hitLogTime) {
        this.hitLogTime = hitLogTime;
    }
}
