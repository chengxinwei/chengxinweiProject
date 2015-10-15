package com.howbuy.cc.basic.failover.aop;

import com.howbuy.cc.basic.failover.annation.FailOver;
import com.howbuy.cc.basic.failover.handler.common.FailOverHandler;
import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.logger.CCLoggerUtil;
import com.howbuy.cc.basic.namespace.CoreOperationSource;
import com.howbuy.cc.basic.spring.SpringBean;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class FailOverInterceptor implements MethodInterceptor, Serializable , InitializingBean {

    private final static CCLogger logger = CCLogger.getLogger(FailOverInterceptor.class);

    private final static Map<String, AtomicInteger> currentExecuteMethod = new ConcurrentHashMap<>();

    @Autowired
    private CoreOperationSource coreOperationSource;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method method = invocation.getMethod();
        String methodName = method.getName();

        FailOver failOver = method.getAnnotation(FailOver.class);
        int maxThreadCount = failOver.maxThreadCount();

        //初始化 双重检查避免线程安全问题，内部添加sync避免性能问题
        AtomicInteger atomicInteger = currentExecuteMethod.get(methodName);
        if(atomicInteger == null){
            synchronized (currentExecuteMethod){
                atomicInteger = currentExecuteMethod.get(methodName);
                if(atomicInteger == null) {
                    currentExecuteMethod.put(methodName, new AtomicInteger(0));
                    atomicInteger = currentExecuteMethod.get(methodName);
                }
            }
        }

        //原子类自增避免线程安全问题
        int count = atomicInteger.incrementAndGet();

        FailOverHandler failOverHandler = SpringBean.getBean(failOver.handlerClass());

        //当前处理已经大于最大线程，调用handler做异常处理
        if(count > maxThreadCount){
            try {
                Class<?> clazz = invocation.getThis().getClass();
                Object[] args = invocation.getArguments();
                this.logWarn("failover_overMaxThread", count, maxThreadCount, clazz, method, args);
                return failOverHandler.handlerFailOver(failOver, clazz , method, args);
            }finally {
                atomicInteger.decrementAndGet();
            }
        }
        try {
           return executeMethod(failOver , invocation , failOverHandler);
        }finally {
            atomicInteger.decrementAndGet();
        }
    }


    /**
     * 执行具体方法，考虑超时
     * @param failOver
     * @param invocation
     * @param failOverHandler
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Object executeMethod(final FailOver failOver , final MethodInvocation invocation , final FailOverHandler failOverHandler) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Object> result = executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Object object;
                try {
                    object = invocation.proceed();
                    failOverHandler.setFailOverValue(failOver , invocation.getThis().getClass(), invocation.getMethod(), invocation.getArguments() , object);
                } catch (Throwable throwable) {
                    throw new Exception(throwable);
                }
                return object;
            }
        });
        Object object;
        try {
            object = result.get(failOver.maxExecuteTimeout() , TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            Class<?> clazz = invocation.getThis().getClass();
            this.logWarn("failover_timeout" , null , failOver.maxExecuteTimeout() , clazz , invocation.getMethod() , invocation.getArguments());
            return failOverHandler.handlerFailOver(failOver , invocation.getThis().getClass(), invocation.getMethod(), invocation.getArguments());
        }
        return object;
    }


    private void logWarn(String header , Integer count , Integer maxThreadCount ,
                         Class<?> targetClass ,
                         Method method ,
                         Object[] args){
        String[] logInfo = new String[5];
        logInfo[0] = count + "/" + maxThreadCount;
        logInfo[1] = targetClass.getSimpleName();
        logInfo[2] = method.getName();
        logInfo[3] = Json.toJson(args, JsonFormat.compact());
        logger.warn(header , logInfo);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isNotEmpty(coreOperationSource.getFailOverLog())){
            CCLoggerUtil.clearAndAddFileLog(logger, coreOperationSource.getFailOverLog());
        }
    }
}