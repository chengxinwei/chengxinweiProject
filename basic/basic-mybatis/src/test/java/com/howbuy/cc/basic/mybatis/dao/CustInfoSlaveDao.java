package com.howbuy.cc.basic.mybatis.dao;

import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.annotation.CCNameSpaceMapper;
import com.howbuy.cc.basic.mybatis.model.CustInfo;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
@Service
@CCDatasourceRoute("slave")
public class CustInfoSlaveDao extends MybatisCommonDao<CustInfo>{

    public CustInfo selectOne() {
        return super.selectOne("selectOne" , null);
    }
}
