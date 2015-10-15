package com.howbuy.cc.basic.filter;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.howbuy.cc.basic.namespace.CoreOperationSource;
import com.howbuy.cc.basic.util.MatchUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;

/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class FilterExcludeBeanPostProcessor implements BeanPostProcessor , ApplicationContextAware {

    @Autowired
    private CoreOperationSource coreOperationSource;
    private final String uuidLoggerFilter = "dubboUUIDLoggerFilter";
    private final String accessLoggerFilter = "dubboAccessLoggerFilter";
    private final String codeLoggerFilter = "dubboCodeLoggerFilter";
    private final String requestLoggerFilter = "dubboRequestLoggerFilter";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    /**
     * dubbo 服务端日志，基于bean实现
     * dubbo的service会生成代理类继承 SericeBean 内部有filter属性，修改其filter即可
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Set<String> set = new HashSet<>();
        if(bean instanceof ServiceBean){
            ServiceBean serviceBean = (ServiceBean)bean;
            String interfaceName = serviceBean.getInterface();
            for(String excludeClass : coreOperationSource.getExcludeLogDetailClassList()) {
                if (MatchUtil.wildMatch(excludeClass, interfaceName)){
                    set.add(interfaceName);
                }
            }
            String[] filter;
            if(StringUtils.isNotEmpty(coreOperationSource.getAccessLog())){
                if(StringUtils.isNotEmpty(serviceBean.getFilter())) {
                    filter = new String[]{serviceBean.getFilter(), uuidLoggerFilter, accessLoggerFilter, codeLoggerFilter};
                }else{
                    filter = new String[]{uuidLoggerFilter, accessLoggerFilter, codeLoggerFilter};
                }
            }else{
                if(StringUtils.isNotEmpty(serviceBean.getFilter())) {
                    filter = new String[]{serviceBean.getFilter() , uuidLoggerFilter , codeLoggerFilter};
                }else{
                    filter = new String[]{uuidLoggerFilter, codeLoggerFilter};
                }
            }

            serviceBean.setFilter(StringUtils.join(filter , ","));
            return bean;
        }
        coreOperationSource.getExcludeLogDetailClassList().addAll(set);
        return bean;
    }


    /**
     * 调用端日志，调用段日志和服务端日志不一样，因为调用段并没有继承统一的一个类
     * dubbo在通过proxy生成类之后，直接调用，所以通过获取ReferenceBean来实现
     * ReferenceBean是dubbo在生成调用端代理类的配置内容，包括了filter 修改器内部的filter即可。
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(StringUtils.isEmpty(coreOperationSource.getRequestLog())){
            return;
        }
        Map<String , ReferenceBean> referenceBeanMap = applicationContext.getBeansOfType(ReferenceBean.class);
        for(Map.Entry<String , ReferenceBean> entry : referenceBeanMap.entrySet()){
            ReferenceBean referenceBean = entry.getValue();
            String interfaceName = referenceBean.getInterfaceClass().getName();
            for(String excludeClass : coreOperationSource.getExcludeLogDetailClassList()) {
                if (MatchUtil.wildMatch(excludeClass, interfaceName)){
                    coreOperationSource.getExcludeLogDetailClassList().add(interfaceName);
                }
            }
            if(StringUtils.isNotEmpty(referenceBean.getFilter())){
                referenceBean.setFilter(referenceBean.getFilter() + "," + requestLoggerFilter);
            }else{
                referenceBean.setFilter(requestLoggerFilter);
            }
        }
    }
}
