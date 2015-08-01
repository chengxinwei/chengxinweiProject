package chengxinwei.datas.reader.redis;

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
        Jedis jedis = new Jedis("192.168.220.220", 6379);
        jedisThreadLocal.set(jedis);
        return jedis;
    }


}
