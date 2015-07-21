package com.howbuy.cc.basic.mybatis.dao;

import com.howbuy.cc.basic.mybatis.BaseTest;
import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.dao.callback.ExecuteCallBack;
import com.howbuy.cc.basic.mybatis.model.Announce;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/7/20.
 */
@Service
@CCDatasourceRoute("slave")
public class AnnounceDao extends MybatisCommonDao<Announce>{

    

    public void batchUpdate(List<Announce> announceList){
        final Map<String,Object> map = new HashMap<>();
        map.put("announceList" , announceList);
        super.execute(new ExecuteCallBack<Void>(){

            @Override
            public Void execute(String namespace, SqlSessionTemplate sqlSessionTemplate) {
                sqlSessionTemplate.update(namespace + ".batchUpdate"  ,  map);
                return null;
            }

        });
    }

}
