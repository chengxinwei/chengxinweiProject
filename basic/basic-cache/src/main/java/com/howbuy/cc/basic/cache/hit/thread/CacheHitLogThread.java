package com.howbuy.cc.basic.cache.hit.thread;

import com.howbuy.cc.basic.cache.hit.HitCount;
import com.howbuy.cc.basic.cache.hit.aop.CacheHitOperationSource;
import com.howbuy.cc.basic.logger.CCLogger;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.PatternLayout;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xinwei.cheng on 2015/8/13.
 */
public class CacheHitLogThread extends Thread{

    private CacheHitOperationSource cacheHitOperationSource;

    CCLogger ccLogger = CCLogger.getLogger(this.getClass() , false);

    public void init(){
        initLogger(cacheHitOperationSource.getHitLogPath());
        this.start();
    }

    @Override
    public void run(){
        while(true){
            try {
                for (Map.Entry<String, AtomicInteger> entry : HitCount.hitCount.entrySet()) {
                    String[] strAry = new String[4];
                    strAry[0] = entry.getKey();
                    Integer hitCount = entry.getValue().get();
                    Integer missCount = HitCount.missCount.get(entry.getKey()) == null ?
                            0 : HitCount.missCount.get(entry.getKey()).get();
                    Double hitRat = 0D;
                    strAry[1] = hitCount.toString();
                    strAry[2] = missCount.toString();
                    if (hitCount != 0) {
                        BigDecimal c = new BigDecimal(hitCount / Double.parseDouble(String.valueOf(hitCount + missCount)));
                        hitRat = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    strAry[3] = hitRat.toString();
                    ccLogger.info("缓存命中率", strAry);
                }
                HitCount.del();
            }catch(Exception e){
                ccLogger.error(e.getMessage() , e);
            }
            try {
                Thread.sleep(cacheHitOperationSource.getHitLogTime());
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }


    public void initLogger(String hitLog){
        ccLogger.getOriginalLog().removeAllAppenders();

        ccLogger.getOriginalLog().setAdditivity(false);
        DailyRollingFileAppender appender=new DailyRollingFileAppender();
        appender.setFile(hitLog);
        appender.setDatePattern("'.'yyyy-MM-dd");
        PatternLayout layout=new PatternLayout("%-d{yyyy-MM-dd HH:mm:ss}  %m%n");
        appender.setLayout(layout);
        appender.setAppend(true);
        appender.activateOptions();
        ccLogger.getOriginalLog().addAppender(appender);
    }


    public CacheHitOperationSource getCacheHitOperationSource() {
        return cacheHitOperationSource;
    }

    public void setCacheHitOperationSource(CacheHitOperationSource cacheHitOperationSource) {
        this.cacheHitOperationSource = cacheHitOperationSource;
    }

}
