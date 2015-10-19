package com.howbuy.cc.basic.boot.filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

/**
 * Created by xinwei.cheng on 2015/9/7.
 */
@Component
public class FilterBean {


    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TestFilter());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
    }

}
