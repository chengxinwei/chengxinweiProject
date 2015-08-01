package com.howbuy.cc.basic.mq.sender.common;

/**
 * TOPIC 发送的父类 (发布订阅)
 * Title: TopicAbstractSender.java
 * @author cheng.xinwei
 */
public abstract class TopicAbstractSender extends AbstractSender{


	public TopicAbstractSender(){
		super.setSub(true);
	}
	
	
}
