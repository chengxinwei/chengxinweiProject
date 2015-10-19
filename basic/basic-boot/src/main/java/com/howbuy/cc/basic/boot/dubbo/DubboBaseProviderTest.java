package com.howbuy.cc.basic.boot.dubbo;

import com.howbuy.cc.basic.test.interfac.DubboBaseInterfaceTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xinwei.cheng on 2015/8/20.
 */
@RestController("DubboBaseProviderTest")
public class DubboBaseProviderTest implements DubboBaseInterfaceTest {


    @Override
    @RequestMapping("/ping")
    public String ping() {
        return "ok";
    }

    @Override
    public void exception() {
        throw new NullPointerException("testException");
    }

    @Override
    @RequestMapping("/getConfigByApi")
    public String getConfigByApi(String key){
        System.out.println(key);
        return "ok";
    }

    @Override
    public String getConfigByAnno() {
        return "ok";
    }

}
