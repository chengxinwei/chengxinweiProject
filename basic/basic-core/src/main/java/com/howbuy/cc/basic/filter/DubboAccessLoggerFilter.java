package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.rpc.*;
import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.namespace.CoreOperationSource;
import com.howbuy.cc.basic.spring.SpringBean;

/**
 * Created by xinwei.cheng on 2015/5/27.
 */
public class DubboAccessLoggerFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        CoreOperationSource coreOperationSource = SpringBean.getBean(CoreOperationSource.class);
        CCLogger logger = coreOperationSource.getAccessLogObj();
        if(logger == null){
            return invoker.invoke(invocation);
        }

        String[] logInfo = new String[5];

        boolean excludeLogDetail = coreOperationSource.getExcludeLogDetailClassList().contains(invoker.getInterface().getName());
        Result result = LoggerFilterUtil.executeAndGetLoggerInfo(logInfo, invoker, invocation , excludeLogDetail);
        if(result.hasException()){
            logger.warn("access.fail" , logInfo);
        }else{
            logger.info("access.success" , logInfo);
        }
        return result;
    }
}