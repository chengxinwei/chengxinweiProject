package com.howbuy.cc.basic.namespace;

import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.logger.CCLoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import java.util.*;

/**
 * Created by xinwei.cheng on 2015/8/19.
 */
public class CoreOperationSource implements InitializingBean{


    private CCLogger accessLogObj;
    private CCLogger requestLogObj;
    private String accessLog;
    private String requestLog;
    private String excludeLogDetail;
    private Set<String> excludeLogDetailClassList = new HashSet<>();
    private String failOverLog;
    private boolean useConfigServer;

    public String getAccessLog() {
        return accessLog;
    }

    public void setAccessLog(String accessLog) {
        this.accessLog = accessLog;
    }

    public String getRequestLog() {
        return requestLog;
    }

    public void setRequestLog(String requestLog) {
        this.requestLog = requestLog;
    }

    public String getExcludeLogDetail() {
        return excludeLogDetail;
    }

    public void setExcludeLogDetail(String excludeLogDetail) {
        this.excludeLogDetail = excludeLogDetail;
    }

    public Set<String> getExcludeLogDetailClassList() {
        return excludeLogDetailClassList;
    }

    public void setExcludeLogDetailClassList(Set<String> excludeLogDetailClassList) {
        this.excludeLogDetailClassList = excludeLogDetailClassList;
    }

    public String getFailOverLog() {
        return failOverLog;
    }

    public void setFailOverLog(String failOverLog) {
        this.failOverLog = failOverLog;
    }

    public CCLogger getAccessLogObj() {
        return accessLogObj;
    }

    public void setAccessLogObj(CCLogger accessLogObj) {
        this.accessLogObj = accessLogObj;
    }

    public CCLogger getRequestLogObj() {
        return requestLogObj;
    }

    public void setRequestLogObj(CCLogger requestLogObj) {
        this.requestLogObj = requestLogObj;
    }

    public boolean isUseConfigServer() {
        return useConfigServer;
    }

    public void setUseConfigServer(boolean useConfigServer) {
        this.useConfigServer = useConfigServer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isNotEmpty(excludeLogDetail)){
            excludeLogDetailClassList = new HashSet<>(Arrays.asList(excludeLogDetail.split(",")));
        }

        if(StringUtils.isNotEmpty(this.getRequestLog())){
            this.requestLogObj = CCLoggerUtil.clearAndAddFileLog(CCLogger.getLogger("requestLog"), this.getRequestLog());
        }
        if(StringUtils.isNotEmpty(this.getAccessLog())){
            this.accessLogObj = CCLoggerUtil.clearAndAddFileLog(CCLogger.getLogger("accessLog"), this.getAccessLog());
        }
    }

}
