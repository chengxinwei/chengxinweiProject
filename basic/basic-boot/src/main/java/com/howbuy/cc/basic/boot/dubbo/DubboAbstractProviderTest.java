package com.howbuy.cc.basic.boot.dubbo;

import com.howbuy.cc.basic.test.interfac.DubboAbstractInterfaceTest;
import com.howbuy.cc.basic.test.model.AbstractUser;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xinwei.cheng on 2015/9/8.
 */
@RestController("DubboAbstractProviderTest")
public class DubboAbstractProviderTest implements DubboAbstractInterfaceTest{


    @Override
    public AbstractUser getUser() {
        AbstractUser abstractUser = new AbstractUser() {
        };
        return abstractUser;
    }
}
