package com.howbuy.cc.basic.mq.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ActivemqSender {

	String value()  ;

    boolean logDetail() default true;
}
