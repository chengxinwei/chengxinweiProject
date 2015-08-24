package com.howbuy.cc.basic.util;

import com.howbuy.cc.basic.common.BaseTest;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/20.
 */
public class SerializationTest {

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        Map<String,Object> params = new HashMap<>();
        params.put("a" , "b");

        byte[] bytes = SerializationUtil.serialize(params);
        System.out.println(SerializationUtil.deserialize(bytes));

    }
}
