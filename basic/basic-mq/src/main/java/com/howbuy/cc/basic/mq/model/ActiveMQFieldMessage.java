package com.howbuy.cc.basic.mq.model;

import com.howbuy.cc.basic.mq.sender.common.AbstractSender;

import net.sf.cglib.proxy.MethodProxy;

public class ActiveMQFieldMessage {

//	private MethodProxy methodProxy;
//
//	private Object[] args;

	private Object obj;

	private AbstractSender abstractSender;

	public ActiveMQFieldMessage(AbstractSender abstractSender , Object obj) {
		super();
//		this.methodProxy = methodProxy;
//		this.args = args;
		this.abstractSender = abstractSender;
		this.obj = obj;
	}


//	public MethodProxy getMethodProxy() {
//		return methodProxy;
//	}
//
//
//	public void setMethodProxy(MethodProxy methodProxy) {
//		this.methodProxy = methodProxy;
//	}
//
//
//	public Object[] getArgs() {
//		return args;
//	}
//
//	public void setArgs(Object[] args) {
//		this.args = args;
//	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}


//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + Arrays.hashCode(args);
//		result = prime * result
//				+ ((methodProxy == null) ? 0 : methodProxy.hashCode());
//		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
//		return result;
//	}




	public AbstractSender getAbstractSender() {
		return abstractSender;
	}


	public void setAbstractSender(AbstractSender abstractSender) {
		this.abstractSender = abstractSender;
	}

//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ActiveMQFieldMessage other = (ActiveMQFieldMessage) obj;
//		if (!Arrays.equals(args, other.args))
//			return false;
//		if (methodProxy == null) {
//			if (other.methodProxy != null)
//				return false;
//		} else if (!methodProxy.equals(other.methodProxy))
//			return false;
//		if (this.obj == null) {
//			if (other.obj != null)
//				return false;
//		} else if (!this.obj.equals(other.obj))
//			return false;
//		return true;
//	}
//


}
