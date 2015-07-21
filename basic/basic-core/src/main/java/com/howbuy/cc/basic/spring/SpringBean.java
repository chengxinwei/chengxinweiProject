package com.howbuy.cc.basic.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * spring bean 的所有bean存储
 * Created by xinwei.cheng on 2015/7/8.
 */
@SuppressWarnings("unused")
public class SpringBean implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    private static Map<String, BeanDefinitionBuilder> registBeanMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBean.applicationContext != null) {
            throw new IllegalStateException("applicationContext is already exists");
        }
        SpringBean.applicationContext = applicationContext;
        for (Map.Entry<String, BeanDefinitionBuilder> entry : registBeanMap.entrySet()) {
            regist(entry.getKey(), entry.getValue());
        }
    }


    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }


    public static void regist(String beanName, BeanDefinitionBuilder beanDefinitionBuilder) {
        if (applicationContext == null) {
            registBeanMap.put(beanName, beanDefinitionBuilder);
            return;
        }

        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
