package com.howbuy.cc.basic.namespace;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

/**
 * Created by xinwei.cheng on 2015/8/19.
 */
public class CoreOperationSource implements InitializingBean{

    private String accessLog;
    private String requestLog;
    private String excludeLogDetail;
    private Set<String> excludeLogDetailClassList = new HashSet<>();
    private String failOverLog;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isNotEmpty(excludeLogDetail)){
            excludeLogDetailClassList = new HashSet<>(Arrays.asList(excludeLogDetail.split(",")));
        }
    }

    public static void main(String[] args) {

    }
}
