package com.howbuy.cc.basic.dubbo.execute.service.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.howbuy.cc.basic.dubbo.execute.model.ExecuteInfo;
import com.howbuy.cc.basic.dubbo.execute.service.common.ExecuteService;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

/**
 * 主要负责调用动态调用dubbo的服务端
 * Created by xinwei.cheng on 2015/7/10.
 */
public class DubboExecute {


    public static Object execute(String zookeeperHost, Class<?> clazz, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-for-rest");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(zookeeperHost);

        ReferenceConfig<?> rc = new ReferenceConfig();

        rc.setApplication(application);
        rc.setRegistry(registry);
        rc.setInterface(clazz);
        rc.setProtocol("dubbo");

        Object obj = rc.get();
        return method.invoke(obj, args);
    }

}
