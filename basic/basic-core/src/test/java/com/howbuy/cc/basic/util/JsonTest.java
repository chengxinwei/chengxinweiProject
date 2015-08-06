package com.howbuy.cc.basic.util;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/4.
 */
public class JsonTest {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("a"  , "b");
        System.out.println(Json.toJson(map, JsonFormat.compact()));
    }

}
