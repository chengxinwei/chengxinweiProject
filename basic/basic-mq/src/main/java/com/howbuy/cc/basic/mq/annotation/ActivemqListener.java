package com.howbuy.cc.basic.mq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.howbuy.cc.basic.mq.constant.MQConstant;
import org.springframework.stereotype.Service;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivemqListener {

	String value()  ;
	
	String systemName() default MQConstant.DETAULT_NONE;
	
	//消费者持久化
    boolean isSubscriptionDurable() default false;
	
	//订阅者的ID
	String clientId() default MQConstant.DETAULT_NONE;

    //线程数
    int threadCount() default 1;

}
