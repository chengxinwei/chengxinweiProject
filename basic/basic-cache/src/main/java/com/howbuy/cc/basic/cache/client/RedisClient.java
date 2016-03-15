package com.howbuy.cc.basic.cache.client;

import com.howbuy.cc.basic.cache.constant.CacheCloseConstant;
import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.tp.common.redis.core.RedisCallback;
import com.howbuy.tp.common.redis.core.ops.GeneralOps;
import com.howbuy.tp.common.redis.core.ops.KvOps;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.ShardedJedis;


/**
 * Created by xinwei.cheng on 2015/8/28.
 */
public class RedisClient extends CacheClient{

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 根据key设置对应的value
     * key转换byte数组 value转换序列化
     * @param key 设置的key
     * @param value 设置的值
     * @param timeout second
     */
    @Override
    protected void doPut(final String key, final Object value, final int timeout) {
        //关闭缓存开关，如果关闭缓存则不获取直接返回空
        Boolean close = Configuration.getBoolean(CacheCloseConstant.REDIS_CLOSE);
        if(close != null && close){
            logger.warn("redis doPut close is open , key["+key+"] , close["+close+"]");
            return;
        }
        if(StringUtils.isEmpty(key)){
            logger.warn("redis doPut key is null , key["+key+"]");
            return;
        }
        execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(ShardedJedis shardedJedis) {
                if(timeout > 0) {
                    shardedJedis.setex(serialize(key, true), timeout, serialize(value, false));
                }else{
                    shardedJedis.set(serialize(key, true) , serialize(value, false));
                }
                return null;
            }
        });
    }


    /**
     * 根据key获取对应的value
     * key转换byte数组 value转换序列化
     * @param key 获取的key
     * @return 返回序列化之后的对象
     */
    @Override
    protected Object doGet(final String key) {
        //关闭缓存开关，如果关闭缓存则不获取直接返回空
        Boolean close = Configuration.getBoolean(CacheCloseConstant.REDIS_CLOSE);
        if(close != null && close){
            logger.warn("redis doGet close is open , key["+key+"] , close["+close+"]");
            return null;
        }
        if(StringUtils.isEmpty(key)){
            logger.warn("redis doGet key is null , key["+key+"]");
            return null;
        }
        return execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(ShardedJedis shardedJedis) {
                byte[] resultByteAry = shardedJedis.get(serialize(key , true));
                if(ArrayUtils.isEmpty(resultByteAry)){
                    return null;
                }
                return deserialize(resultByteAry , false);
            }
        });
    }

    @Override
    protected void doClear(final String key) {
        //关闭缓存开关，如果关闭缓存则不获取直接返回空
        Boolean close = Configuration.getBoolean(CacheCloseConstant.REDIS_CLOSE);
        if(close != null && close){
            return;
        }
        if(StringUtils.isEmpty(key)){
            return;
        }
        execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(ShardedJedis shardedJedis) {
                shardedJedis.del(serialize(key , true));
                return null;
            }
        });
    }

    public long incr(String key){
        final String realKey = CacheKeyGenerator.getKeyStr(key);
        if(StringUtils.isEmpty(key)){
            return 0L;
        }
        return execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(ShardedJedis shardedJedis) {
                return shardedJedis.incr(serialize(realKey , true));
            }
        });
    }


    public long decr(String key){
        final String realKey = CacheKeyGenerator.getKeyStr(key);
        if(StringUtils.isEmpty(key)){
            return 0L;
        }
        return execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(ShardedJedis shardedJedis) {
                return shardedJedis.decr(serialize(realKey, true));
            }
        });
    }


    public String getOriginal(final String cacheName, final Object cacheVar){
        if(cacheName == null || cacheVar == null){
            return null;
        }
        final String key = CacheKeyGenerator.getKeyStr(cacheName, cacheVar.toString());

        //关闭缓存开关，如果关闭缓存则不获取直接返回空
        Boolean close = Configuration.getBoolean(CacheCloseConstant.REDIS_CLOSE);
        if(close != null && close){
            logger.warn("redis doGet close is open , key["+key+"] , close["+close+"]");
            return null;
        }
        if(StringUtils.isEmpty(key)){
            logger.warn("redis doGet key is null , key["+key+"]");
            return null;
        }

        if(StringUtils.isEmpty(key)){
            return null;
        }

        String obj = execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        });


        if(obj == null){
            this.hitLog(cacheName, false);
            return null;
        }
        this.hitLog(cacheName, true);
        return obj;
    }



}
