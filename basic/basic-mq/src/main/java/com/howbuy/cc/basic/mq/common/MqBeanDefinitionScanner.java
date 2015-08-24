package com.howbuy.cc.basic.mq.common;

import com.howbuy.cc.basic.mq.annotation.ActivemqListener;
import com.howbuy.cc.basic.mq.annotation.ActivemqSender;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Set;

/**
 * Created by xinwei.cheng on 2015/8/18.
 */
public class MqBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public MqBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public Set<BeanDefinitionHolder> doScan(String basePackages) {
        super.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                if(metadataReader.getAnnotationMetadata().hasAnnotation(ActivemqListener.class.getName())){
                    return true;
                }
                if(metadataReader.getAnnotationMetadata().hasAnnotation(ActivemqSender.class.getName())){
                    return true;
                }
                return false;
            }
        });
        return super.doScan(StringUtils.tokenizeToStringArray(basePackages, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }
}
