package com.howbuy.cc.basic;

/**
 * Created by xinwei.cheng on 2015/7/23.
 */

import com.alibaba.dubbo.common.bytecode.ClassGenerator;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

public class TestClassLoader {

    public void testLoadClass() throws Exception {
        ClassPool pool = ClassGenerator.getClassPool(getClass().getClassLoader());
        CtClass cls = pool.makeInterface("com.alibaba.dubbo.registry.zookeeper.TestBubbo");
        cls.addMethod(CtMethod.make("public void show();", cls));
        cls.toClass();
    }

    public static void main(String[] args) throws Exception {

        TestClassLoader clt = new TestClassLoader();
        clt.testLoadClass();

        ClassPool pool = ClassGenerator.getClassPool(TestClassLoader.class.getClassLoader());

        CtClass cls = pool.get("com.alibaba.dubbo.registry.zookeeper.TestBubbo");
        cls.detach();
        CtClass cls1 = pool.makeInterface("com.alibaba.dubbo.registry.zookeeper.TestBubbo");
        cls1.addMethod(CtMethod.make("public void show111();", cls1));
        cls1.toClass();

    }

}
