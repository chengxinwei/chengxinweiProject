package com.howbuy.cc.basic.mybatis.datasourceRoute;

import com.howbuy.cc.basic.mybatis.util.RouteBeanNameGenerator;
import com.howbuy.cc.basic.spring.SpringBean;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
public class SqlSessionFactoryRoute{

    private Set<String> dataSourceBeanNameSet;
    private String configLocation;
    private String typeAliasesPackage;
    private List<String> mapperLocations;
    private String defaultDatasourceBeanName;


    public Set<String> getDataSourceBeanNameSet() {
        return dataSourceBeanNameSet;
    }

    public void setDataSourceBeanNameSet(Set<String> dataSourceBeanNameSet) {
        this.dataSourceBeanNameSet = dataSourceBeanNameSet;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public List<String> getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(List<String> mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getDefaultDatasourceBeanName() {
        return defaultDatasourceBeanName;
    }

    public void setDefaultDatasourceBeanName(String defaultDatasourceBeanName) {
        this.defaultDatasourceBeanName = defaultDatasourceBeanName;
    }


    @PostConstruct
    public void init() throws BeansException {
        for(String dataSourceBeanName : dataSourceBeanNameSet){
            regist(dataSourceBeanName);
        }
        if(!dataSourceBeanNameSet.contains(defaultDatasourceBeanName)){
            regist(defaultDatasourceBeanName);
        }
    }

    public void regist(String dataSourceBeanName){
        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(SqlSessionFactoryBean.class);
        beanDefinitionBuilder.addPropertyReference("dataSource", dataSourceBeanName);
        beanDefinitionBuilder.addPropertyValue("configLocation" , configLocation);
        beanDefinitionBuilder.addPropertyValue("typeAliasesPackage" , typeAliasesPackage);
        beanDefinitionBuilder.addPropertyValue("mapperLocations" , mapperLocations);
        SpringBean.regist(RouteBeanNameGenerator.getBeanName(dataSourceBeanName) , beanDefinitionBuilder);
    }

}
