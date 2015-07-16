package com.howbuy.cc.basic.mybatis;

import com.howbuy.cc.basic.mybatis.dao.CustInfoDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
public class MybatisTest extends BaseTest{

    @Autowired
    private CustInfoDao custInfoDao;


    @Test
    public void selectOne(){
        System.out.println(custInfoDao.selectOne(new HashMap<String, Object>()));

    }

}
