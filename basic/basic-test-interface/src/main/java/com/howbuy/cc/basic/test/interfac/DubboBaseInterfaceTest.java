package com.howbuy.cc.basic.test.interfac;

/**
 * Created by xinwei.cheng on 2015/8/20.
 */
public interface DubboBaseInterfaceTest {


    public String ping();

    public void exception();

    public String getConfigByApi(String key);

    public String getConfigByAnno();

}
