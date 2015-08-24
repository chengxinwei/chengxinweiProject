package com.howbuy.cc.basic.mq.namespace;

/**
 * Created by xinwei.cheng on 2015/8/13.
 */
@SuppressWarnings("unused")
public class MqOperationSource {

    private String brokerUrl;
    private boolean afterTransaction;
    private String basePackage;

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public boolean isAfterTransaction() {
        return afterTransaction;
    }

    public void setAfterTransaction(boolean afterTransaction) {
        this.afterTransaction = afterTransaction;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}