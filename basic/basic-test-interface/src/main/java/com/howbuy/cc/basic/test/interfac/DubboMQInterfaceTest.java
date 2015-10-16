package com.howbuy.cc.basic.test.interfac;

/**
 * Created by xinwei.cheng on 2015/10/10.
 */
public interface DubboMQInterfaceTest {

    public String sendQueue(String text);

    public String sendTopic(String text);

    public String sendVirtual(String text);

}
