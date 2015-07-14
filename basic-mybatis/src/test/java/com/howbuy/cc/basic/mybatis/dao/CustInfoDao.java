package com.howbuy.cc.basic.mybatis.dao;

import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.model.CustInfo;
import org.springframework.stereotype.Service;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
@Service
@CCDatasourceRoute("cim")
public class CustInfoDao extends MybatisCommonDao<CustInfo>{


}
