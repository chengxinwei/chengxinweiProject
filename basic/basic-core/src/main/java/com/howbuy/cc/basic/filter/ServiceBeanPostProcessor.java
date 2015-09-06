//package com.howbuy.cc.basic.filter;
//
//import com.alibaba.dubbo.config.spring.ServiceBean;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//
///**
// * Created by xinwei.cheng on 2015/8/28.
// */
//public class ServiceBeanPostProcessor implements BeanPostProcessor {
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if(bean instanceof ServiceBean){
//            ServiceBean serviceBean = (ServiceBean)bean;
//            if(StringUtils.isNotEmpty(serviceBean.getFilter())){
//                serviceBean.setFilter(serviceBean.getFilter() + "");
//            }
//        }
//        return bean;
//    }
//}
