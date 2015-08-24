package com.howbuy.cc.basic.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by xinwei.cheng on 2015/8/17.
 */
@Aspect
@Component
public class AspectJTest {


    @Pointcut("execution(* com.howbuy.cc.basic.test.aop.UserService.*(..))")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinpoint) throws Throwable {
        System.out.println("before ");
        Object obj = joinpoint.proceed();
        System.out.println("after");
        return obj;
    }

}
