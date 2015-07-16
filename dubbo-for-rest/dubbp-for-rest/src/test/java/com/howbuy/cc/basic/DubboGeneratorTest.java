package com.howbuy.cc.basic;

import com.howbuy.cc.basic.dubbo.generator.DubboXmlGenerator;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/7/10.
 */
public class DubboGeneratorTest extends BaseTest{


    @Test
    public void test() throws IOException {
        DubboXmlGenerator.generator("com.howbuy.datax.service.taxonomy.service.ICustLoginService");
    }

}
