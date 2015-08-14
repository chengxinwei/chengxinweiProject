package com.chengxinwei.datas.reader.redis;

import com.howbuy.cc.basic.config.Configuration;
import redis.clients.jedis.Jedis;

/**
 * Created by xinwei.cheng on 2015/7/30.
 */
public class RedisClient {

    static ThreadLocal<Jedis> jedisThreadLocal = new ThreadLocal<Jedis>();

    public static Jedis getJedis(){
        if(jedisThreadLocal.get()!=null){
            return jedisThreadLocal.get();
        }
        Jedis jedis = new Jedis(Configuration.get("redis.server.url") ,
                Integer.parseInt(Configuration.get("redis.server.port")));
        jedisThreadLocal.set(jedis);
        return jedis;
    }


}
