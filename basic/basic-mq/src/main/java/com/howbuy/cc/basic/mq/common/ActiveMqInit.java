package com.howbuy.cc.basic.mq.common;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import com.howbuy.cc.basic.mq.constant.MQConstant;
import com.howbuy.cc.basic.mq.listener.common.*;
import com.howbuy.cc.basic.mq.listener.common.container.QueueMessageListenerContainer;
import com.howbuy.cc.basic.mq.listener.common.container.TopicMessageListenerContainer;
import com.howbuy.cc.basic.mq.listener.common.container.VirtualMessageListenerContainer;
import com.howbuy.cc.basic.mq.sender.common.AbstractSender;
import com.howbuy.cc.basic.spring.SpringBean;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * MQ的初始化bean
 * cheng.xinwei
 * Title: ActiveMqInit.java
 * @author cheng.xinwei
 */
public class ActiveMqInit {

    @Autowired
    private ActiveMQConnectionFactory activeMQConnectionFactory;


    @PostConstruct
    public void init(){
		initSender();
		initListener();
    }

    /**
     * 初始化sender
     */
    public void initSender(){
    	Map<String, AbstractSender>  map = SpringBean.getApplicationContext().getBeansOfType(AbstractSender.class);
    	for(String key :map.keySet()){
    		initSender(map.get(key));
    	}
    }
    
    /**
     * 初始化某个sender
     * @param abstractSender 初始化发送端
     */
    private void initSender(AbstractSender abstractSender){
    	ActivemqSender activemqSender =  abstractSender.getClass().getAnnotation(ActivemqSender.class);
		if(activemqSender != null){
			abstractSender.setDestinationName(activemqSender.value());
            abstractSender.setDestinationName(activemqSender.value());
			abstractSender.setConnectionFactory(activeMQConnectionFactory);
		}
    }

    /**
     * 初始化listener
     */
    public void initListener() {
     	Map<String, AbstractListener>  beans = SpringBean.getApplicationContext().getBeansOfType(AbstractListener.class);
     	
     	for(String key :beans.keySet()){
     		AbstractListener abstractListener =  beans.get(key);
     		//获取activemqListener注解
     		ActivemqListener activemqListenerAnno = abstractListener.getClass().getAnnotation(ActivemqListener.class);
     		if(activemqListenerAnno != null){
     		    //获取消息类型
     			String beanName;
 				Class<?> registClazz;

 				Map<String , Object> params = new HashMap<>();
 				
 				//依据不同的消息类型做不同的处理
     			if(abstractListener instanceof QueueAbstractListener){
     				registClazz = QueueMessageListenerContainer.class;
     			}else if(abstractListener instanceof TopicAbstractListener){
     				//TOPIC 需要是否持久化
     				if(activemqListenerAnno.isSubscriptionDurable()){
     					params.put("subscriptionDurable", true);
     					params.put("clientId", activemqListenerAnno.clientId());
     				}
     				registClazz =  TopicMessageListenerContainer.class;
     			}else if(abstractListener instanceof VirtualAbstractListener){
     				//VIRTUAL 需要判断是否指定了systemName
     				registClazz =  VirtualMessageListenerContainer.class;
     				if(activemqListenerAnno.systemName() == null || activemqListenerAnno.systemName().equals(MQConstant.DETAULT_NONE)){
     					throw new IllegalArgumentException("VirtualAbstractListener 必须设置 systemName");
     				}
     				params.put("sysName", activemqListenerAnno.systemName());
     			}else{
     				throw new RuntimeException("abstractListener 没有继承 QueueAbstractListener | TopicAbstractListener | VirtualAbstractListener ， 无法识别是哪类监听端" );
     			}
     			
     			//beanName 
     			beanName = QueueMessageListenerContainer.class.getSimpleName() + "." +  abstractListener.getClass().getName();

     			GenericBeanDefinition registBean = new GenericBeanDefinition();
                //初始化参数
 				params.put("connectionFactory" , activeMQConnectionFactory);
 				params.put("messageListener" , abstractListener);
 				params.put("destinationName" , activemqListenerAnno.value());
                params.put("concurrentConsumers" , activemqListenerAnno.threadCount());

 				registBean.setBeanClass(registClazz);
 				registBean.setPropertyValues(new MutablePropertyValues(params));
 				//注册
 				SpringBean.regist(beanName , registBean);
     		}
     	}
    }
}
