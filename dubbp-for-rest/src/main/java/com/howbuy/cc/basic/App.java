package com.howbuy.cc.basic;

import com.howbuy.cc.basic.dubbo.generator.DubboService;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Hello world!
 */
public class App {


    final static  Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws IOException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, ParserConfigurationException, SAXException, InstantiationException, NoSuchMethodException {

        new ClassPathXmlApplicationContext(
                        new String[]{"basic/spring/base-spring.xml"});
        String pomStr = "<dependency>\n" +
                "  <groupId>com.howbuy</groupId>\n" +
                "  <artifactId>crm-label-api2Login-model</artifactId>\n" +
                "  <version>1.0</version>\n" +
                "</dependency>";
        String interfaceClass = "com.howbuy.datax.service.taxonomy.service.ICustLoginService";

        DubboService.generator(pomStr);

        try {
//            DubboService.excute(interfaceClass, "bind", new String[]{"1", "2", "1", "2"});
        }catch (Exception e){
            logger.error(e.getMessage() , e);
        }



        pomStr = "<dependency>\n" +
                "  <groupId>com.howbuy</groupId>\n" +
                "  <artifactId>crm-label-model</artifactId>\n" +
                "  <version>1.3</version>\n" +
                "</dependency>";

        interfaceClass = "com.howbuy.datax.service.taxonomy.service.ICustLabelService";
        DubboService.generator(pomStr);
//        DubboService.excute(interfaceClass, "find", new String[]{"1", "10" , null});
    }
}
