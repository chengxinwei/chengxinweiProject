package com.howbuy.cc.basic.map;

import java.util.HashMap;
import java.util.Map;

/**
 * CC的map类，put之后返回this方便使用
 * Created by xinwei.cheng on 2015/8/3.
 */
@SuppressWarnings("unused")
public class CCHashMap<K,V> extends HashMap<K,V>{

    public CCHashMap putAndReturn(K k, V v){
        super.put(k , v);
        return this;
    }


    public CCHashMap putAllAndReturn(Map<K,V> map){
        super.putAll(map);
        return this;
    }

}
