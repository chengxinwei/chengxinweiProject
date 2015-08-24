package com.howbuy.cc.basic.mq.common.register;

import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.constant.MQConstant;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;

/**
 * Created by xinwei.cheng on 2015/8/18.
 */
public class SenderRegister {

    public static void registSender(ScannedGenericBeanDefinition beanDefinition , Class<?> clazz) {
        beanDefinition.getPropertyValues().add("destinationName" , clazz.getAnnotation(ActivemqSender.class).value());
        beanDefinition.getPropertyValues().add("mqOperationSource" , new RuntimeBeanReference(MQConstant.MQ_OPERATION_SOURCE_BEANNAME));
        beanDefinition.getPropertyValues().add("jmsTemplate" , new RuntimeBeanReference(MQConstant.MQ_JMSTEMPLATE_BEANNAME));
    }

}
