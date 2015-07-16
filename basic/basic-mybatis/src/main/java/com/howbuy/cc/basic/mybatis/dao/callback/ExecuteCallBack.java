package com.howbuy.cc.basic.mybatis.dao.callback;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * 回调函数接口类
 * Created by xinwei.cheng on 2015/7/16.
 */
@SuppressWarnings("unused")
public interface ExecuteCallBack<E>{

    E execute(String namespace , SqlSessionTemplate sqlSessionTemplate);
}
