package com.howbuy.activemq.transaction;

import com.howbuy.activemq.model.User;
import com.howbuy.activemq.sender.QueueSenderTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xinwei.cheng on 2015/8/14.
 */
@Service
public class TransactionTest {

    @Autowired
    private QueueSenderTest queueSenderTest;

    @Transactional
    public void testCommit(){
        queueSenderTest.sendMessage(new User(11));
    }


    @Transactional
    public void testException(){
        throw new RuntimeException("test");
    }

}
