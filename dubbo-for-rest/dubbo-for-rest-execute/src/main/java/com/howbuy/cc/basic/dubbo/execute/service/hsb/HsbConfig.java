package com.howbuy.cc.basic.dubbo.execute.service.hsb;

import com.howbuy.hsb.txio.NetConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/11/30.
 */
public class HsbConfig  implements BeanFactoryAware {

    protected static Map<String,Object> map = new HashMap<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        if( beanFactory instanceof DefaultListableBeanFactory){
            String className = "org.softamis.cluster4spring.support.invocation.DefaultEndpointSelectionPolicy";
            DefaultListableBeanFactory factory = (DefaultListableBeanFactory)beanFactory;
            RootBeanDefinition beanDefinition = new RootBeanDefinition(NetConfig.class);
            beanDefinition.getPropertyValues().addPropertyValues(map);
            beanDefinition.getPropertyValues().add("communMode" , "3");
            beanDefinition.getPropertyValues().add("timeout" , "0");
            beanDefinition.getPropertyValues().add("local" , "false");
            beanDefinition.getPropertyValues().add("endpointSelectionPolicy" , className);
            factory.registerBeanDefinition(className , beanDefinition);

            factory.registerBeanDefinition("hsbClient" , new RootBeanDefinition("com.howbuy.hsb.hsbclient.HSBClient"));
        }
    }
}
