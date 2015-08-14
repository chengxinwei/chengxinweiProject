package com.chengxinwei.datas.reader.data;

import com.chengxinwei.datas.reader.data.common.DataReader;
import com.howbuy.cc.basic.mybatis.annotation.CCNameSpaceMapper;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/7/30.
 */
@Service
@CCNameSpaceMapper("SqlMapper")
public class DbReader extends DataReader {

    @Override
    public Page getData(String tableName , int count, int pageNo, int pageSize) {
        Map<String,Object> params = new HashMap<>();
        params.put("tableName" , tableName);
//        Page page =  super.page("select", count, params, pageNo , pageSize  , "id");

        Map<String,Object> a = new HashMap<>();
        a.put("cate"  , "1");
        a.put("score"  , "2");
        a.put("no"  , "3");

        List<Map> list = new ArrayList<>();
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);

        Page<Map> page = new Page<>(pageSize , pageNo , count);
        page.setPageList(list);
        return page;
    }

    @Override
    public int count(String tableName){
//        Map<String,Object> params = new HashMap<>();
//        params.put("tableName" , tableName);
//        return super.count("count" , params);
        return 100;
    }
}
