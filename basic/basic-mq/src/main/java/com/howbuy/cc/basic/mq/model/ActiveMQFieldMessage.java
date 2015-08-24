package com.howbuy.cc.basic.mq.model;

import com.howbuy.cc.basic.mq.sender.common.AbstractSender;

import net.sf.cglib.proxy.MethodProxy;

public class ActiveMQFieldMessage {

	private Object obj;

	private AbstractSender abstractSender;

	public ActiveMQFieldMessage(AbstractSender abstractSender , Object obj) {
		super();
		this.abstractSender = abstractSender;
		this.obj = obj;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public AbstractSender getAbstractSender() {
		return abstractSender;
	}

	public void setAbstractSender(AbstractSender abstractSender) {
		this.abstractSender = abstractSender;
	}


}
