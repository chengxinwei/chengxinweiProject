package com.howbuy.cc.basic.mq.namespace;

/**
 * Created by xinwei.cheng on 2015/8/13.
 */
@SuppressWarnings("unused")
public class MqOperationSource {

    private boolean afterTransactionSend;
    private String basePackage;
    private String senderLog;
    private String listenerLog;
    private boolean redeliveryListener;
    private long initialRedeliveryDelay;
    private int maximumRedeliveries;

    public boolean isAfterTransactionSend() {
        return afterTransactionSend;
    }

    public void setAfterTransactionSend(boolean afterTransactionSend) {
        this.afterTransactionSend = afterTransactionSend;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getSenderLog() {
        return senderLog;
    }

    public void setSenderLog(String senderLog) {
        this.senderLog = senderLog;
    }

    public String getListenerLog() {
        return listenerLog;
    }

    public void setListenerLog(String listenerLog) {
        this.listenerLog = listenerLog;
    }

    public boolean isRedeliveryListener() {
        return redeliveryListener;
    }

    public void setRedeliveryListener(boolean redeliveryListener) {
        this.redeliveryListener = redeliveryListener;
    }

    public long getInitialRedeliveryDelay() {
        return initialRedeliveryDelay;
    }

    public void setInitialRedeliveryDelay(long initialRedeliveryDelay) {
        this.initialRedeliveryDelay = initialRedeliveryDelay;
    }

    public int getMaximumRedeliveries() {
        return maximumRedeliveries;
    }

    public void setMaximumRedeliveries(int maximumRedeliveries) {
        this.maximumRedeliveries = maximumRedeliveries;
    }
}