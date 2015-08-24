package com.howbuy.cc.basic.mongo.mongoTime;

import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.logger.CCLoggerUtil;
import com.howbuy.cc.basic.mongo.namespace.MongoOperationSource;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class MongoTimeInterceptor implements MethodInterceptor, Serializable , InitializingBean {

    private MongoOperationSource mongoOperationSoure;
    private final CCLogger ccLogger = CCLogger.getLogger(this.getClass() , false);
    private final static String MONGO_LOG = "mongo_log";

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long time = System.currentTimeMillis() - start;
        if(time > mongoOperationSoure.getMongoTimeout()){
            int infoLengthOffset =  2;
            String[] infoAry = new String[invocation.getArguments().length + infoLengthOffset];

            String methodName = invocation.getMethod().getName();
            infoAry[0] = methodName;
            infoAry[1] = String.valueOf(time);
            for(int i = 0 ; i < invocation.getArguments().length ; i++) {
                Object obj = invocation.getArguments()[i];
                if(obj instanceof Query){
                    infoAry[i + infoLengthOffset] = obj.toString();
                }else {
                    infoAry[i + infoLengthOffset] = Json.toJson(obj, JsonFormat.compact());
                }
            }
            ccLogger.info(MONGO_LOG , infoAry);
        }
        return result;
    }

    public MongoOperationSource getMongoOperationSoure() {
        return mongoOperationSoure;
    }

    public void setMongoOperationSoure(MongoOperationSource mongoOperationSoure) {
        this.mongoOperationSoure = mongoOperationSoure;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        CCLoggerUtil.clearAndAddFileLog(ccLogger , mongoOperationSoure.getMongoTimeLog());
    }
}
