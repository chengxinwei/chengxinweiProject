package com.howbuy.cc.basic.mq.intercepter;

import com.howbuy.cc.basic.mq.annotation.ActiveMQTransactional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.howbuy.cc.basic.mq.common.ActiveMQThreadLocal;



@Component
@Aspect
public class ActiveTransactionalIntercepter{
	
	
	@Pointcut("execution(* *.*(..)) && @annotation(activeTransactional)")
	private void activeTransactionalMethod(ActiveMQTransactional activeTransactional) {
	}
	
	
	@Before("activeTransactionalMethod(activeTransactional)")
	public void before(JoinPoint joinPoint,ActiveMQTransactional activeTransactional) throws Exception {
		ActiveMQThreadLocal.openActiveMQTransactional();
	}
	
	
	/**
	 * 当方法跑出异常 判断是否最外层,如果是最外层,删除所有事物嵌套和消息
	 * @author  cheng.xinwei
	 * @description
	 */
	@AfterThrowing("activeTransactionalMethod(activeTransactional)")
	public void afterThrowing(ActiveMQTransactional activeTransactional){
		ActiveMQThreadLocal.closeActiveMQTransactional();
		if(ActiveMQThreadLocal.canCommitActiveMQTransactional()){
			ActiveMQThreadLocal.removeMessage();
			ActiveMQThreadLocal.closeAllActiveMQTransactional();
		}
	}
	
	/**
	 * 方法执行后执行,每次方法执行完成,删除一个事物标记,执行完最外层方法提交事务
	 * @author  cheng.xinwei
	 * @description   
	 * @param activeTransactional
	 * @throws Throwable
	 */
	@AfterReturning("activeTransactionalMethod(activeTransactional)")
	public void afterReturning(ActiveMQTransactional activeTransactional) {
		ActiveMQThreadLocal.closeActiveMQTransactional();
		if(ActiveMQThreadLocal.canCommitActiveMQTransactional()){
			try {
				ActiveMQThreadLocal.commitSendMessage();
			} catch (Throwable e) {
				e.printStackTrace();
			}finally{
				ActiveMQThreadLocal.closeAllActiveMQTransactional();
				ActiveMQThreadLocal.removeMessage();
			}
		}
	}
	
	
}