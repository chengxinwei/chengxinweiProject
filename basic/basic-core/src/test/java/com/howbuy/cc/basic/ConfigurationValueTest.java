package com.howbuy.cc.basic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/7/21.
 */
@Service
public class ConfigurationValueTest {

    @Value("${testValue}")
    private String testValue;

    public String getValue(){
        return testValue;
    }

}
