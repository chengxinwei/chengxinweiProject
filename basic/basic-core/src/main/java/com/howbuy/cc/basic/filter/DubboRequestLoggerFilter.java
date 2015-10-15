package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.logger.CCLoggerUtil;
import com.howbuy.cc.basic.namespace.CoreOperationSource;
import com.howbuy.cc.basic.spring.SpringBean;

/**
 * Created by xinwei.cheng on 2015/5/27.
 */
public class DubboRequestLoggerFilter implements Filter {

    CCLogger ccLogger = CCLogger.getLogger(this.getClass());

    private String requestLog;
    private CoreOperationSource coreOperationSource;

    public DubboRequestLoggerFilter(){
        coreOperationSource = SpringBean.getBean(CoreOperationSource.class);
        if(coreOperationSource == null || StringUtils.isEmpty(coreOperationSource.getRequestLog())){
            return;
        }
        requestLog = coreOperationSource.getRequestLog();
        CCLoggerUtil.clearAndAddFileLog(ccLogger, requestLog);
    }


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if(requestLog == null){
            return invoker.invoke(invocation);
        }

        String[] logInfo = new String[5];
        boolean excludeLogDetail = coreOperationSource.getExcludeLogDetailClassList().contains(invoker.getInterface().getName());
        Result result = LoggerFilterUtil.executeAndGetLoggerInfo(logInfo, invoker, invocation , excludeLogDetail);
        if(result.hasException()){
            ccLogger.warn("request.fail" , logInfo);
        }else{
            ccLogger.info("request.success" , logInfo);
        }
        return result;
    }
}