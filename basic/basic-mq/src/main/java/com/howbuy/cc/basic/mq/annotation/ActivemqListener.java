package com.howbuy.cc.basic.mq.annotation;

import com.howbuy.cc.basic.mq.constant.MQConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivemqListener {

	String value()  ;
	
	String systemName() default MQConstant.DETAULT_NONE;
	
	//订阅者的ID
	String clientId() default MQConstant.DETAULT_NONE;

    //线程数
    int threadCount() default 1;

    int prefetchSize() default 1;

    String selector() default MQConstant.DETAULT_NONE;

    boolean logDetail() default true;

}
