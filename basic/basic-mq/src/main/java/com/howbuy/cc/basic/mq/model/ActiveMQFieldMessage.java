package com.howbuy.cc.basic.mq.model;

import com.howbuy.cc.basic.mq.sender.common.AbstractSender;

import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;

public class ActiveMQFieldMessage {

	private Serializable obj;
	private AbstractSender abstractSender;

	public ActiveMQFieldMessage(AbstractSender abstractSender , Serializable obj) {
		super();
		this.abstractSender = abstractSender;
		this.obj = obj;
	}

	public Serializable getObj() {
		return obj;
	}

	public void setObj(Serializable obj) {
		this.obj = obj;
	}

	public AbstractSender getAbstractSender() {
		return abstractSender;
	}

	public void setAbstractSender(AbstractSender abstractSender) {
		this.abstractSender = abstractSender;
	}

}
