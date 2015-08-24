package com.howbuy.cc.basic.mybatis.dao;

import com.howbuy.cc.basic.mybatis.model.CustInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by xinwei.cheng on 2015/7/8.
 */
@Repository
public class CustInfoMasterDao extends MybatisCommonDao<CustInfo>{

    public CustInfo selectOne(){
        return super.selectOne("selectOne" , null);
    }

    public void delete(Integer id){
        super.deleteById("delete", id);
    }

    public void insert(){
        super.insert("insert", new CustInfo());

    }

    public void update(){
            super.update("update", new CustInfo());
    }

}
