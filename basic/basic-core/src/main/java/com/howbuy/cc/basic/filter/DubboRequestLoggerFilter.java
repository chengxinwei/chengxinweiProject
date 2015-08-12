package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.howbuy.cc.basic.logger.CCLogger;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * Created by xinwei.cheng on 2015/5/27.
 */
public class DubboRequestLoggerFilter implements Filter {

    CCLogger ccLogger = CCLogger.getLogger(this.getClass());

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String[] logInfo = new String[5];

        Result result = LoggerFilterUtil.executeAndGetLoggerInfo(logInfo, invoker, invocation);
        if(result.hasException()){
            ccLogger.warn("request.fail" , logInfo);
        }else{
            ccLogger.info("request.success" , logInfo);
        }
        return result;
    }
}