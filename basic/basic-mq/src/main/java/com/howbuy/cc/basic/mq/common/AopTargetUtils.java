package com.howbuy.cc.basic.mq.common;



import java.lang.reflect.Field;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

/**
 * AOP 工具类
 * @author cheng.xinwei
 * @date              
 * @description
 */
public class AopTargetUtils {

	
	/**
	 * 获取被代理的目标对象
	 * @author  cheng.xinwei
	 * @description   
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	public static Object getTarget(Object proxy) throws Exception {
        
		if(!AopUtils.isAopProxy(proxy)) {
			return proxy;//不是代理对象
		}
		
		//jdk proxy
		if(AopUtils.isJdkDynamicProxy(proxy)) {
			return getJdkDynamicProxyTargetObject(proxy);
		} else { //cglib
			return getCglibProxyTargetObject(proxy);
		}
		
		
        
	}

	/**
	 * 获取CGLIB 被代理的类
	 * @author  cheng.xinwei
	 * @description   
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        
        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        
        return target;
	}

	/**
	 * 获取JDK proxy 被代理的类
	 * @author  cheng.xinwei
	 * @description   
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        
        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
        
        return target;
	}
	
}
