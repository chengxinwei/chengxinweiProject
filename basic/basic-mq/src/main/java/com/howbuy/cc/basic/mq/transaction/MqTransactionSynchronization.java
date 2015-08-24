package com.howbuy.cc.basic.mq.transaction;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;

/**
 * Created by xinwei.cheng on 2015/8/14.
 */
public class MqTransactionSynchronization extends TransactionSynchronizationAdapter {

    private long threadId;

    public MqTransactionSynchronization(long threadId){
        this.threadId = threadId;
    }

    @Override
    public void afterCommit() {
        ActiveMQThreadLocal.setEndTransaction(true);
        ActiveMQThreadLocal.commitSendMessage();
    }

    @Override
    public void afterCompletion(int status){
        ActiveMQThreadLocal.removeEndTransaction();
        ActiveMQThreadLocal.clear();
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MqTransactionSynchronization that = (MqTransactionSynchronization) o;

        return threadId == that.threadId;

    }

    @Override
    public int hashCode() {
        return (int) (threadId ^ (threadId >>> 32));
    }
}