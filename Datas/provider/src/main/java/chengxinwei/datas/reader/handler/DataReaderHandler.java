package chengxinwei.datas.reader.handler;

import chengxinwei.datas.reader.data.DataReader;
import chengxinwei.datas.reader.redis.RedisClient;
import chengxinwei.datas.reader.util.PageNoGenerator;
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

    final static int threadCount = 10;

    public void readData(final String tableName) {

        int count = dbReader.count(tableName);

        if(count == 0){
            return;
        }

        final Page page = new Page<>(dbReader.getPageSize() , 0 , count);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, threadCount, 5, TimeUnit.SECONDS, new ArrayBlockingQueue(1));
        for (int i = 0; i < threadCount; i++) {
            threadPoolExecutor.execute(new Thread() {
                public void run() {
                    while (dbReader.readData(tableName, page)) {
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