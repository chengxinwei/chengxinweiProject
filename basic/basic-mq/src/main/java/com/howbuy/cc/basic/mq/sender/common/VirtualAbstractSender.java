package com.howbuy.cc.basic.mq.sender.common;

/**
 * 虚拟的topic
 * Title: VirtualAbstractSender.java
 * @author cheng.xinwei
 */
public abstract class VirtualAbstractSender extends AbstractSender{
	
	public VirtualAbstractSender(){
		super.setSub(true);
	}

}
