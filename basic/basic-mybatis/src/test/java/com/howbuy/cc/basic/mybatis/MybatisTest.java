package com.howbuy.cc.basic.mybatis;

import com.howbuy.cc.basic.mybatis.common.BaseTest;
import com.howbuy.cc.basic.mybatis.dao.AnnounceDao;
import com.howbuy.cc.basic.mybatis.dao.CustInfoMasterDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
public class MybatisTest extends BaseTest {

    @Autowired
    private CustInfoMasterDao custInfoMasterDao;
    @Autowired
    private AnnounceDao announceDao;

    @Before
    public void before(){
        delete();
        insert();
    }

    public void insert(){
        custInfoMasterDao.insert();
    }

    @Test
    public void selectOne(){
        System.out.println(custInfoMasterDao.selectOne().getCustNo());
    }

    @Test
    public void update(){
        custInfoMasterDao.update();
    }

    public void delete(){
        custInfoMasterDao.delete(1);
    }


}
