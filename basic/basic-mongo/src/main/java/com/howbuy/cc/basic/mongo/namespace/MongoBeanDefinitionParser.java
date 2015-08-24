package com.howbuy.cc.basic.mongo.namespace;

import com.howbuy.cc.basic.mongo.mongoTime.MongoTimeInterceptor;
import com.howbuy.cc.basic.mongo.mongoTime.MongoTimeAdvisor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.w3c.dom.Element;

/**
 * Created by xinwei.cheng on 2015/8/12.
 */
public class MongoBeanDefinitionParser implements BeanDefinitionParser {



    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);

        String mongo = element.getAttribute("mongo");
        String databaseName = element.getAttribute("databaseName");
        String username = element.getAttribute("username");
        String password = element.getAttribute("password");

        String mongoTimeLog = element.getAttribute("mongoTimeLog");
        String mongoTimeout = element.getAttribute("mongoTimeout");

        RootBeanDefinition mongoSourceDefinition = new RootBeanDefinition(MongoOperationSource.class);
        mongoSourceDefinition.getPropertyValues().add("mongoTimeLog" , mongoTimeLog);
        mongoSourceDefinition.getPropertyValues().add("mongoTimeout" , mongoTimeout);
        String sourceBeanName = parserContext.getReaderContext().registerWithGeneratedName(mongoSourceDefinition);

        RootBeanDefinition mongoBeanDefinition = new RootBeanDefinition(MongoTemplate.class);
        ConstructorArgumentValues mongoConstructor = new ConstructorArgumentValues();
        mongoConstructor.addIndexedArgumentValue(0, new RuntimeBeanReference(mongo));
        mongoConstructor.addIndexedArgumentValue(1, databaseName);
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            //设置密码验证
            RootBeanDefinition userCredentialsBeanDefinition = new RootBeanDefinition(UserCredentials.class);
            ConstructorArgumentValues userCredentialConstructor = new ConstructorArgumentValues();
            userCredentialConstructor.addIndexedArgumentValue(0, username);
            userCredentialConstructor.addIndexedArgumentValue(1, password);
            userCredentialsBeanDefinition.setConstructorArgumentValues(userCredentialConstructor);

            String userCredentialBeanName = parserContext.getReaderContext().registerWithGeneratedName(userCredentialsBeanDefinition);
            mongoConstructor.addIndexedArgumentValue(2, new RuntimeBeanReference(userCredentialBeanName));
        }

        mongoBeanDefinition.setConstructorArgumentValues(mongoConstructor);

        parserContext.getReaderContext().registerWithGeneratedName(mongoBeanDefinition);

        if(!StringUtils.isEmpty(mongoTimeLog)) {
            Object eleSource = parserContext.extractSource(element);

            // Create the CacheInterceptor definition.
            RootBeanDefinition interceptorDef = new RootBeanDefinition(MongoTimeInterceptor.class);
            interceptorDef.setSource(eleSource);
            interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            interceptorDef.getPropertyValues().add("mongoOperationSoure", new RuntimeBeanReference(sourceBeanName));
            String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

            RootBeanDefinition advisorDef = new RootBeanDefinition(MongoTimeAdvisor.class);
            advisorDef.setSource(eleSource);
            advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
            parserContext.getRegistry().registerBeanDefinition(MongoTimeAdvisor.class.getName(), advisorDef);

            CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), eleSource);
            compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
            compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, MongoTimeAdvisor.class.getName()));
            parserContext.registerComponent(compositeDef);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(MongoBeanDefinitionParser.class.getMethods()[0].equals(MongoBeanDefinitionParser.class.getMethods()[0]));
    }
}
