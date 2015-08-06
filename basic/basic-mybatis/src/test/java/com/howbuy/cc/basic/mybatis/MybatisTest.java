package com.howbuy.cc.basic.mybatis;

import com.howbuy.cc.basic.mybatis.dao.CustInfoMasterDao;
import com.howbuy.cc.basic.mybatis.dao.CustInfoSlaveDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
public class MybatisTest extends BaseTest{

    @Autowired
    private CustInfoMasterDao custInfoMasterDao;
    @Autowired
    private CustInfoSlaveDao custInfoSlaveDao;


    @Test
    public void selectOne(){
        custInfoMasterDao.delete(1);
        custInfoMasterDao.insert();
        custInfoMasterDao.update();
        System.out.println(custInfoMasterDao.selectOne().getCustNo());

    }
}
