package com.howbuy.cc.basic.dubbo;

import com.howbuy.cc.basic.common.BaseTest;
import com.howbuy.cc.basic.spring.SpringBean;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */

@ContextConfiguration(locations = {"classpath:test-spring-dubbo-provider.xml" , "classpath:test-spring-dubbo-consumer.xml"})
public class DubboConsumerTest extends BaseTest{


    @Test
    public void testSuccess(){
        DubboInterfaceTest dubboInterfaceTest = (DubboInterfaceTest)SpringBean.getBean("dubboReferenceTest");
        System.out.println(dubboInterfaceTest.getDate());
    }


    @Test
    public void testException(){
        DubboInterfaceTest dubboInterfaceTest = (DubboInterfaceTest)SpringBean.getBean("dubboReferenceTest");
        try {
            dubboInterfaceTest.exception();
        }catch (NullPointerException e){
            if(e.getMessage().equals("testException")){
                return;
            }
            throw e;
        }

    }

}
