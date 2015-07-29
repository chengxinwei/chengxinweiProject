package com.howbuy.cc.basic.test.dubbo;

import com.howbuy.cc.basic.spring.SpringBean;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */

public class DubboConsumerTest{


    public static void testSuccess(){
        DubboInterfaceTest dubboInterfaceTest = (DubboInterfaceTest)SpringBean.getBean("dubboReferenceTest");
        System.out.println(dubboInterfaceTest.getDate());
    }


    public static void testException(){
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
