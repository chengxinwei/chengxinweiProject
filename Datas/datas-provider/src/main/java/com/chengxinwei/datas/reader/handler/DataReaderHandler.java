package com.chengxinwei.datas.reader.handler;

import com.chengxinwei.datas.model.Datas;
import com.chengxinwei.datas.reader.data.common.DataReader;
import com.chengxinwei.datas.reader.redis.RedisClient;
import com.chengxinwei.datas.reader.util.PageNoGenerator;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xinwei.cheng on 2015/7/31.
 */
@Service
public class DataReaderHandler {

    @Autowired
    private DataReader dbReader;

    private int threadCount = 10;


    /**
     * 根据获得表名读取对应的内容
     * @param datas
     */
    public void readData(final Datas datas) {
        String tableName = datas.getTableName();

        int count = dbReader.count(tableName);

        if(count == 0){
            return;
        }

        final Page page = new Page<>(dbReader.getPageSize() , 0 , count);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, threadCount, 5, TimeUnit.SECONDS, new ArrayBlockingQueue(1));
        for (int i = 0; i < threadCount; i++) {
            threadPoolExecutor.execute(new Thread() {
                public void run() {
                    while (dbReader.readData(datas, page)) {
                    }
                }
            });
        }

        while (true) {
            if (threadPoolExecutor.getActiveCount() == 0) {
                RedisClient.getJedis().del(PageNoGenerator.getPageNoKey(tableName));
                break;
            }
        }
    }


}