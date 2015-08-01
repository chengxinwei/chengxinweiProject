package chengxinwei.datas.reader.data;

import com.howbuy.cc.basic.mybatis.annotation.CCNameSpaceMapper;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        Page page =  super.page("select", count, params, pageNo , pageSize  , "id");
        return page;
    }

    @Override
    public int count(String tableName){
        Map<String,Object> params = new HashMap<>();
        params.put("tableName" , tableName);
        return super.count("count" , params);
    }
}
