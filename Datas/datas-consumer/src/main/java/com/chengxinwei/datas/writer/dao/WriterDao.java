package com.chengxinwei.datas.writer.dao;

import com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/10.
 */
@Repository
public class WriterDao extends MybatisCommonDao<Map>{

    public void insert(String tableName ,Map<String,Object> map){
        Map<String,Object> params = new HashMap<>();
        params.put("tableName" , tableName);
        params.put("map" , map);
        super.insert("insert" , params);
    }

}
