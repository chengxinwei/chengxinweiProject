package com.howbuy.cc.basic.mq.connection;

import com.howbuy.cc.basic.mq.namespace.MqOperationSource;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by xinwei.cheng on 2015/9/8.
 */
public class ActivemqConnectionFactory extends ActiveMQConnectionFactory implements InitializingBean{

    private MqOperationSource mqOperationSource;

    public void afterPropertiesSet() throws Exception{
        super.getRedeliveryPolicy().setInitialRedeliveryDelay(mqOperationSource.getInitialRedeliveryDelay());
        super.getRedeliveryPolicy().setMaximumRedeliveries(mqOperationSource.getMaximumRedeliveries());
    }

    public MqOperationSource getMqOperationSource() {
        return mqOperationSource;
    }

    public void setMqOperationSource(MqOperationSource mqOperationSource) {
        this.mqOperationSource = mqOperationSource;
    }
}
