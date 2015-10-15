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
public class DubboAccessLoggerFilter implements Filter {

    private CCLogger ccLogger = CCLogger.getLogger(this.getClass());
    private String accessLog;
    private CoreOperationSource coreOperationSource;

    public DubboAccessLoggerFilter(){
        coreOperationSource = SpringBean.getBean(CoreOperationSource.class);
        if(coreOperationSource == null || StringUtils.isEmpty(coreOperationSource.getAccessLog())){
            return;
        }
        accessLog = coreOperationSource.getAccessLog();
        CCLoggerUtil.clearAndAddFileLog(ccLogger , accessLog);
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if(accessLog == null){
            return invoker.invoke(invocation);
        }

        String[] logInfo = new String[5];

        boolean excludeLogDetail = coreOperationSource.getExcludeLogDetailClassList().contains(invoker.getInterface().getName());
        Result result = LoggerFilterUtil.executeAndGetLoggerInfo(logInfo, invoker, invocation , excludeLogDetail);
        if(result.hasException()){
            ccLogger.warn("access.fail" , logInfo);
        }else{
            ccLogger.info("access.success" , logInfo);
        }
        return result;
    }
}