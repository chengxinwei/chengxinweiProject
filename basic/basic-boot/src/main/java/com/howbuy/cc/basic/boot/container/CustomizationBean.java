package com.howbuy.cc.basic.boot.container;

/**
 * Created by xinwei.cheng on 2015/9/7.
 */

import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.AccessLogValve;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CustomizationBean {

    final String maxThreads = "10";
    final String maxConnections = "10";
    final String connectionTimeout = "100";
    final String protocol = "org.apache.coyote.http11.Http11NioProtocol";
    final String contextPath = "/boot";
    final int port = 8081;
    final int sessionTimeout = 30;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port);
        factory.setSessionTimeout(sessionTimeout , TimeUnit.MINUTES);
        factory.setProtocol(protocol);
        factory.setContextPath(contextPath);
        AccessLogValve accessLogValve = new AccessLogValve();
        accessLogValve.setPrefix("localhost_access_log");
        accessLogValve.setSuffix(".log");
        accessLogValve.setDirectory("/logs");
        accessLogValve.setPattern("common");
//        accessLogValve.s("localhost_access_log.");
        factory.addContextValves(accessLogValve);
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("maxThreads", maxThreads);
                connector.setProperty("maxConnections" , maxConnections);
                connector.setProperty("connectionTimeout" , connectionTimeout);
            }
        });

        //        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notFound.html"));
        return factory;
    }
}