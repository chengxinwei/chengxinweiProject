package com.howbuy.cc.basic.spring;

import com.howbuy.cc.basic.common.BaseTest;
import org.junit.Test;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public class SpringBeanTest extends BaseTest{

    @Test
    public void test(){
        System.out.println(SpringBean.getApplicationContext());
    }

}
