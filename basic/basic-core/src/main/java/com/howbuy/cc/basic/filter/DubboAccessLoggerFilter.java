package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.rpc.*;
import com.howbuy.cc.basic.constant.CommonConstant;
import com.howbuy.cc.basic.logger.CCLogger;
import org.nutz.json.Json;

import java.util.Date;

/**
 * Created by xinwei.cheng on 2015/5/27.
 */
public class DubboAccessLoggerFilter implements Filter {

    CCLogger ccLogger = CCLogger.getLogger(this.getClass());



    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String[] logInfo = new String[5];

        logInfo[0] = invoker.getInterface().getName();
        logInfo[1] = invocation.getMethodName();
        logInfo[2] = Json.toJson(invocation.getArguments());

        Date now = new Date();
        Result result = invoker.invoke(invocation);
        long useTime = new Date().getTime() - now.getTime();
        logInfo[4] = String.valueOf(useTime);
        String message;
        if(result.hasException()){
            logInfo[3] = result.getException().toString();
            message = "dubbo请求失败";
        }else{
            logInfo[3] = Json.toJson(result.getValue());
            message = "dubbo请求成功";
        }
        ccLogger.info(CommonConstant.DUBBO_ACCESS , message , logInfo);
        return result;
    }
}