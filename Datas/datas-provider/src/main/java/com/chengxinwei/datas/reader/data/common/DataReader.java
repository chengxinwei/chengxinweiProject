package com.chengxinwei.datas.reader.data.common;

import com.chengxinwei.datas.model.Datas;
import com.chengxinwei.datas.reader.redis.RedisClient;
import com.chengxinwei.datas.reader.sender.DataSender;
import com.chengxinwei.datas.reader.util.PageNoGenerator;
import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 获取数据
 * Created by xinwei.cheng on 2015/7/30.
 */
@Service
public abstract class DataReader extends MybatisCommonDao<Map>{

    private int pageSize = 1000;

    @Autowired
    private DataSender dataSender;

    CCLogger ccLogger = CCLogger.getLogger(this.getClass());

    public abstract Page<Map> getData(String tableName, int count , int pageNo, int pageSize);

    public abstract int count(String tableName);

    public boolean readData(final Datas datas, Page page){
        String tableName = datas.getTableName();
        String pageNoKey = PageNoGenerator.getPageNoKey(tableName);

        int count = page.getTotalCount();
        int totalPage = page.getTotalPage();

        Jedis jedis = RedisClient.getJedis();
        long pageNo = jedis.incr(pageNoKey);
        if (pageNo > totalPage) {
            return false;
        }

        page = this.getData(tableName, count, (int)pageNo, pageSize);

        datas.setDataList(page.getPageList());
        dataSender.sendMessage(datas);

        ccLogger.info("数据查询完成【表名】" + tableName + ",【页数】"+pageNo + ",【条数】"+page.getPageList().size() + "，发送至MQ，等待数据处理");
        return true;
    }

    public int getPageSize(){
        return pageSize;
    }

}
