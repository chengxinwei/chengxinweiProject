package com.howbuy.activemq;

import com.howbuy.activemq.common.BaseTest;
import com.howbuy.activemq.sender.*;
import com.howbuy.activemq.transaction.TransactionTest;
import com.howbuy.cc.basic.map.CCHashMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/25.
 */
public class MqTest extends BaseTest{

    @Autowired
    private ExceptionVirtualSenderTest exceptionVirtualSenderTest;
    @Autowired
    private ExceptionQueueSenderTest exceptionQueueSenderTest;
    @Autowired
    private TransactionTest transactionTest;
    @Autowired
    private QueueSenderTest queueSenderTest;
    @Autowired
    private TopicSenderTest topicSenderTest;
    @Autowired
    private SelectQueueSenderTest selectQueueSenderTest;
    @Autowired
    private VirtualSenderTest virtualSenderTest;

    @Test
    public void exceptionVirtualTest(){
        exceptionVirtualSenderTest.sendMessage("virtual.exception");
        try {
            Thread.sleep(10*10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void exceptionQueueTest(){
        exceptionQueueSenderTest.sendMessage("queue.exception");
        try {
            Thread.sleep(10*10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void transactionCommitTest(){
        transactionTest.testCommit();
    }

    @Test
    public void transactionExceptionTest(){
        transactionTest.testException();
    }

    @Test
    public void topicTest() throws InterruptedException {
        topicSenderTest.sendMessage("topic.test");
        Thread.sleep(1*1000);
    }

    @Test
    public void topicClientIdTest() throws InterruptedException {
        topicSenderTest.sendMessage("topic.test");
        Thread.sleep(1*1000);
    }

    @Test
    public void queueTest() throws InterruptedException {
        queueSenderTest.sendMessage("queue.test");
        Thread.sleep(1*1000);
    }

    @Test
    public void virtualTest() throws InterruptedException {
        virtualSenderTest.sendMessage("virtual.test");
        Thread.sleep(1*1000);
    }

    @Test
    public void selectorTest() throws InterruptedException {
        Map<String,String> params = new CCHashMap<String,String>().putAndReturn("age", "1");
        selectQueueSenderTest.sendMessage("selector.test.A" , params);

        params = new CCHashMap<String,String>().putAndReturn("age", "2");
        selectQueueSenderTest.sendMessage("selector.test.B" , params);
        Thread.sleep(1*1000);

    }


    @Test
    public void queueSendAlways() throws InterruptedException {
        int i = 0;
        while (true){
            queueSenderTest.sendMessage(i);
        }
    }

    @Test
       public void consumer() throws InterruptedException {
        Thread.sleep(600*1000);
    }



}
