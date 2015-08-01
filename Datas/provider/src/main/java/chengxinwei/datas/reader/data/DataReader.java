package chengxinwei.datas.reader.data;

import chengxinwei.datas.reader.redis.RedisClient;
import chengxinwei.datas.reader.util.PageNoGenerator;
import com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.nutz.json.Json;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 获取数据
 * Created by xinwei.cheng on 2015/7/30.
 */
public abstract class DataReader extends MybatisCommonDao<Map>{

    private int pageSize = 1000;

    public abstract Page<Map> getData(String tableName, int count , int pageNo, int pageSize);

    public abstract int count(String tableName);

    public boolean readData(final String tableName, Page page){
        String pageNoKey = PageNoGenerator.getPageNoKey(tableName);

        int count = page.getTotalCount();
        int totalPage = page.getTotalPage();

        Jedis jedis = RedisClient.getJedis();
        long pageNo = jedis.incr(pageNoKey);
        if (pageNo > totalPage) {
            return false;
        }

        page = this.getData(tableName, count, (int)pageNo, pageSize);
        System.out.println(page);
        return true;
    }

    public int getPageSize(){
        return pageSize;
    }

}
