package com.howbuy.cc.basic.mq.common;

import com.howbuy.cc.basic.mq.model.ActiveMQFieldMessage;
import com.howbuy.cc.basic.mq.sender.common.AbstractSender;

import java.util.ArrayList;
import java.util.List;

public class ActiveMQThreadLocal {

	//当前事物待发送的消息
	private static final ThreadLocal<List<ActiveMQFieldMessage>> activeMQMessageThreadLocal = new ThreadLocal<>();
	
	//当前线程执行是否开启了事物
	private static final ThreadLocal<Integer> openActiveMQTransactionalThreadLocal = new ThreadLocal<>();
	
	
	/**
	 * 把待执行的消息放入本地线程中,等待commit
	 * @author  cheng.xinwei
	 * @description   
	 * @param obj
	 */
	public static void putMessage(AbstractSender abstractSender , Object obj){
		List<ActiveMQFieldMessage> activeMQMessageList = activeMQMessageThreadLocal.get();
		activeMQMessageList = activeMQMessageList == null ? new ArrayList<ActiveMQFieldMessage>() : activeMQMessageList;
		ActiveMQFieldMessage activeMQFieldMessage = new ActiveMQFieldMessage(abstractSender , obj);
		activeMQMessageList.add(activeMQFieldMessage);
		activeMQMessageThreadLocal.set(activeMQMessageList);;
	}
	
	/**
	 * commit 提交事物 , 发送消息
	 * @author  cheng.xinwei
	 * @description   
	 * @throws Throwable
	 */
	public static void commitSendMessage() throws Throwable{
		List<ActiveMQFieldMessage> activeMQMessageList = activeMQMessageThreadLocal.get();
		if(activeMQMessageList == null){
			return;
		}
		
		for(int i = 0 ; i < activeMQMessageList.size() ; i++){
			ActiveMQFieldMessage activeMQFieldMessage = activeMQMessageList.get(i);
			AbstractSender abstractSender = activeMQFieldMessage.getAbstractSender();
			Object sendMessage = activeMQFieldMessage.getObj();
			if(sendMessage instanceof String){
				abstractSender.sendMessage((String)sendMessage);
			}else{
				throw new Exception("message:" +sendMessage + "can not send!message class:"+sendMessage.getClass());
			}
		}
	}
	
	/**
	 * 清空消息
	 * @author  cheng.xinwei
	 * @description   
	 * @throws Exception
	 */
	public static void removeMessage() {
		activeMQMessageThreadLocal.remove();
	}
	
	
	/**
	 * 开启MQ事物
	 * @author  cheng.xinwei
	 * @description   
	 * @throws Exception
	 */
	public static void openActiveMQTransactional() {
		Integer transactionalStack = openActiveMQTransactionalThreadLocal.get();
		transactionalStack  = transactionalStack == null? 0 : transactionalStack;
		transactionalStack++;
		openActiveMQTransactionalThreadLocal.set(transactionalStack);
	}
	
	/**
	 * 关闭MQ事物
	 * @author  cheng.xinwei
	 * @description   
	 * @throws Exception
	 */
	public static void closeActiveMQTransactional() {
		Integer transactionalStack = openActiveMQTransactionalThreadLocal.get();
		transactionalStack  = transactionalStack == null? 0 : transactionalStack;
		if(transactionalStack == 0){
			return ;
		}
		transactionalStack--;
		openActiveMQTransactionalThreadLocal.set(transactionalStack);
	}
	
	/**
	 * 关闭MQ事物
	 * @author  cheng.xinwei
	 * @description   
	 * @throws Exception
	 */
	public static void closeAllActiveMQTransactional() {
		openActiveMQTransactionalThreadLocal.remove();
	}
	
	/**
	 * 是否开启事物
	 * @author  cheng.xinwei
	 * @description   
	 * @throws Exception
	 */
	public static boolean isOpenActiveMQTransactional(){
		return openActiveMQTransactionalThreadLocal.get()!= null && openActiveMQTransactionalThreadLocal.get() > 0 ;
	}
	
	
	/**
	 * 是否可以提交事物
	 * @author  cheng.xinwei
	 * @description   
	 * @return
	 */
	public static boolean canCommitActiveMQTransactional(){
		return openActiveMQTransactionalThreadLocal.get() == null || openActiveMQTransactionalThreadLocal.get() == 0 ;
	}
	
	
}
