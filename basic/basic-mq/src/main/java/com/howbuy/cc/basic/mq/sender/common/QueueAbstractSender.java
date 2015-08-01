package com.howbuy.cc.basic.mq.sender.common;



/**
 * PTP发送的父类(队列)
 * Title: QueueAbstractSender.java
 * @author cheng.xinwei
 */
public abstract class QueueAbstractSender extends AbstractSender{

	public QueueAbstractSender(){
		super.setSub(false);
	}
	
}
