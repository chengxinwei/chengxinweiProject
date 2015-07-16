package com.howbuy.cc.basic;

import com.howbuy.cc.basic.dubbo.generator.JarGenerator;
import com.howbuy.cc.basic.dubbo.generator.model.Pom;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/7/10.
 */
public class JarGeneratorTest extends BaseTest{

    @Test
    public void test() throws ParserConfigurationException, SAXException, IOException {
        Pom pom = JarGenerator.getPomByStr("<dependency>\n" +
                "  <groupId>com.howbuy.cc.basic</groupId>\n" +
                "  <artifactId>basic-core</artifactId>\n" +
                "  <version>1.0.0</version>\n" +
                "</dependency>");
        JarGenerator.getJarByPom(pom);
    }
}
