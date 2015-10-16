package com.howbuy.cc.basic.mybatis.dao;

import com.howbuy.cc.basic.model.Page;
import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.dao.callback.ExecuteCallBack;
import com.howbuy.cc.basic.mybatis.model.Announce;
import org.mybatis.spring.SqlSessionTemplate;
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

        super.execute("batchUpdate" , new ExecuteCallBack<Void>(){
            @Override
            public Void execute(String fullSqlId, SqlSessionTemplate sqlSessionTemplate) {
                sqlSessionTemplate.update(fullSqlId  ,  map);
                return null;
            }
        });
    }

    public List<Announce> updateAndSelect(){
        return super.selectList("updateAndSelect" , new HashMap<String, Object>());
    }


    public Page<Announce> page(){
        return super.page("page" , "count" , new HashMap<String, Object>() , 1 , 2 , null);
    }

}
