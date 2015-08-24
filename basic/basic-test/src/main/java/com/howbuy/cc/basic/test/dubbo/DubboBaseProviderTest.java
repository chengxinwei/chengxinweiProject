package com.howbuy.cc.basic.test.dubbo;

import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.test.interfac.DubboBaseInterfaceTest;
import com.howbuy.cc.basic.test.interfac.DubboCacheInterfaceTest;
import com.howbuy.cc.basic.test.model.Aaaa;
import com.howbuy.cc.basic.test.mybatis.dao.AaaaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/8/20.
 */
public class DubboBaseProviderTest implements DubboBaseInterfaceTest {


    @Autowired
    private AaaaDao aaaaDao;
    @Value("${test.value}")
    private String testValue;

    @Override
    public String ping() {
        return "ok";
    }

    @Override
    public void exception() {
        throw new NullPointerException("testException");
    }

    @Override
    public String getConfigByApi(String key){
        return Configuration.get(key);
    }

    @Override
    public String getConfigByAnno() {
        return testValue;
    }

}
