package com.howbuy.cc.basic.mq.transaction;

import com.howbuy.cc.basic.mq.model.ActiveMQFieldMessage;
import com.howbuy.cc.basic.mq.sender.common.AbstractSender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActiveMQThreadLocal {

	//当前事物待发送的消息
	private static final ThreadLocal<List<ActiveMQFieldMessage>> activeMQMessageThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<Boolean> isEndTransactionThreadLocal = new ThreadLocal<>();


    public static boolean isEndTransaction(){
        return isEndTransactionThreadLocal.get() != null ? isEndTransactionThreadLocal.get() : false ;
    }

    public static void setEndTransaction(boolean isTransaction){
        isEndTransactionThreadLocal.set(isTransaction);
    }

    public static void removeEndTransaction(){
        isEndTransactionThreadLocal.remove();
    }

	/**
	 * 把待执行的消息放入本地线程中,等待commit
	 * @author  cheng.xinwei
	 * @description   
	 * @param obj
	 */
	public static void putMessage(AbstractSender abstractSender , Serializable obj){
		List<ActiveMQFieldMessage> activeMQMessageList = activeMQMessageThreadLocal.get();
		activeMQMessageList = activeMQMessageList == null ? new ArrayList<ActiveMQFieldMessage>() : activeMQMessageList;
		ActiveMQFieldMessage activeMQFieldMessage = new ActiveMQFieldMessage(abstractSender , obj);
		activeMQMessageList.add(activeMQFieldMessage);
		activeMQMessageThreadLocal.set(activeMQMessageList);
	}
	
	/**
	 * commit 提交事物 , 发送消息
	 * @author  cheng.xinwei
	 * @description   
	 * @throws Throwable
	 */
	public static void commitSendMessage(){
		List<ActiveMQFieldMessage> activeMQMessageList = activeMQMessageThreadLocal.get();
		if(activeMQMessageList == null){
			return;
		}
		
		for(int i = 0 ; i < activeMQMessageList.size() ; i++){
			ActiveMQFieldMessage activeMQFieldMessage = activeMQMessageList.get(i);
			AbstractSender abstractSender = activeMQFieldMessage.getAbstractSender();
            abstractSender.sendMessage(activeMQFieldMessage.getObj());
		}
	}
	
	/**
	 * 清空消息
	 * @author  cheng.xinwei
	 * @description   
	 * @throws Exception
	 */
	public static void clear() {
		activeMQMessageThreadLocal.remove();
	}

}
