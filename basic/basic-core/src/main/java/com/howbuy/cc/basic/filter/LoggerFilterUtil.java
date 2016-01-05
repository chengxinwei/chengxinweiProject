package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class LoggerFilterUtil {

    public static Result executeAndGetLoggerInfo(String[] logInfo, Invoker<?> invoker, Invocation invocation , boolean excludeLogDetail){

        logInfo[0] = invoker.getInterface().getName();
        logInfo[1] = invocation.getMethodName();
        logInfo[2] = Json.toJson(invocation.getArguments(), JsonFormat.compact());

        long start = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long useTime = System.currentTimeMillis() - start;
        if(!excludeLogDetail) {
            if (result.hasException()) {
                logInfo[3] = result.getException().toString();
            } else {
                logInfo[3] = Json.toJson(result.getValue(), JsonFormat.compact());
            }
        }else{
            logInfo[3] = "";
        }
        logInfo[4] = String.valueOf(useTime);
        return result;
    }

}
