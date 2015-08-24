package com.howbuy.cc.basic.mq.common;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.common.register.ListenerRegister;
import com.howbuy.cc.basic.mq.common.register.SenderRegister;
import com.howbuy.cc.basic.mq.namespace.MqOperationSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;

import java.util.Set;

/**
 * Created by xinwei.cheng on 2015/8/18.
 */
public class BeanScanner implements BeanDefinitionRegistryPostProcessor {

    private MqOperationSource mqOperationSource;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MqBeanDefinitionScanner classPathMqBeanDefinitionScanner = new MqBeanDefinitionScanner(registry);
        Set<BeanDefinitionHolder> beanDefinitionHolderSet = classPathMqBeanDefinitionScanner.doScan(mqOperationSource.getBasePackage());
        for(BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolderSet) {
            ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition)beanDefinitionHolder.getBeanDefinition();
            Class<?> clazz;
            try {
                clazz = Class.forName(scannedGenericBeanDefinition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if(clazz.isAnnotationPresent(ActivemqSender.class)){
                SenderRegister.registSender(scannedGenericBeanDefinition, clazz);
            }else if(scannedGenericBeanDefinition.getMetadata().hasAnnotation(ActivemqListener.class.getName())){
                ListenerRegister.registListener(registry , beanDefinitionHolder.getBeanName() , clazz );
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}


    public MqOperationSource getMqOperationSource() {
        return mqOperationSource;
    }

    public void setMqOperationSource(MqOperationSource mqOperationSource) {
        this.mqOperationSource = mqOperationSource;
    }
}
