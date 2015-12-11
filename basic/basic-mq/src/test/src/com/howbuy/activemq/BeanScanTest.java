package com.howbuy.activemq;

import com.howbuy.activemq.model.User;
import com.howbuy.activemq.sender.QueueSenderTest;
import com.howbuy.activemq.sender.TopicSenderTest;
import com.howbuy.activemq.sender.VirtualSenderTest;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xinwei.cheng on 2015/8/18.
 */
public class BeanScanTest {


    @Test
    public void test(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean-scan.xml");

        TopicSenderTest topicSenderTest = classPathXmlApplicationContext.getBean(TopicSenderTest.class);

        VirtualSenderTest virtualSenderTest = classPathXmlApplicationContext.getBean(VirtualSenderTest.class);

        QueueSenderTest queueSenderTest = classPathXmlApplicationContext.getBean(QueueSenderTest.class);
        queueSenderTest.sendMessage("queueSenderTest");
        topicSenderTest.sendMessage("topicSenderTest");
        virtualSenderTest.sendMessage("virtualSenderTest");
    }

}
