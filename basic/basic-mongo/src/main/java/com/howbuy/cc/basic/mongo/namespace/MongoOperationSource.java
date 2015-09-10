package com.howbuy.cc.basic.mongo.namespace;

/**
 * Created by xinwei.cheng on 2015/8/19.
 */
public class MongoOperationSource {

    private String mongoLog;
    private int mongoLogTimeout;

    public String getMongoLog() {
        return mongoLog;
    }

    public void setMongoLog(String mongoLog) {
        this.mongoLog = mongoLog;
    }

    public int getMongoLogTimeout() {
        return mongoLogTimeout;
    }

    public void setMongoLogTimeout(int mongoLogTimeout) {
        this.mongoLogTimeout = mongoLogTimeout;
    }
}
