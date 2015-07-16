package com.howbuy.cc.basic.cache.client;

import com.howbuy.cc.basic.cache.util.CacheKeyGenerator;
import com.howbuy.cc.basic.util.SerializationUtil;
import com.howbuy.tp.common.redis.core.RedisCallback;
import com.howbuy.tp.common.redis.core.ops.GeneralOps;
import org.apache.log4j.Logger;
import redis.clients.jedis.ShardedJedis;

import java.io.IOException;

/**
 * redis 集成架构组的 jar包
 * 封装了基本操作 支持序列化的对象
 * Created by xinwei.cheng on 2015/7/7.
 */
public class RedisCacheClient extends GeneralOps {

    final static Logger logger = Logger.getLogger(RedisCacheClient.class);


    /**
     * 根据key获取对应的value
     * key转换byte数组 value转换序列化
     * @param key 获取的key
     * @return 返回序列化之后的对象
     */
    public static Object get(final String key) {
        final byte[] keyByteAry = CacheKeyGenerator.getKeyByteAry(key);
        if (keyByteAry == null || keyByteAry.length == 0) {
            return null;
        }
        return execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(ShardedJedis shardedJedis) {
                try {
                    byte[] resultByteAry = shardedJedis.get(keyByteAry);
                    if(resultByteAry == null || resultByteAry.length == 0){
                        return null;
                    }
                    return SerializationUtil.deserialize(resultByteAry);
                } catch (IOException | ClassNotFoundException e) {
                    logger.error(e.getMessage() , e);
                }
                return null;
            }
        });
    }


    /**
     * 根据key设置对应的value
     * key转换byte数组 value转换序列化
     * @param key 设置的key
     * @param value 设置的值
     * @param timeout second
     */
    public static void set(final String key , final Object value , final int timeout) {
        final byte[] keyByteAry = CacheKeyGenerator.getKeyByteAry(key);
        if (keyByteAry == null || keyByteAry.length == 0) {
            return;
        }
        execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(ShardedJedis shardedJedis) {
                try {
                    shardedJedis.setex(keyByteAry, timeout, SerializationUtil.serialize(value));
                } catch (IOException e) {
                    logger.error(e.getMessage() , e);
                }
                return null;
            }
        });
    }


    /**
     * 删除对应的key
     * @param key key
     */
    public static void del(String key){
        final byte[] keyByteAry = CacheKeyGenerator.getKeyByteAry(key);
        if (keyByteAry == null || keyByteAry.length == 0) {
            return;
        }
        execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(ShardedJedis shardedJedis) {
                shardedJedis.del(keyByteAry);
                return null;
            }
        });
    }



    public static ShardedJedis getShardedJedis(){
        return execute(new RedisCallback<ShardedJedis>() {
            @Override
            public ShardedJedis doInRedis(ShardedJedis shardedJedis) {
                return shardedJedis;
            }
        });
    }
}
