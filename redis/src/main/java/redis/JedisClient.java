package redis;

import redis.clients.jedis.Jedis;

/**
 * Created by xinwei.cheng on 2015/5/29.
 */
public final class JedisClient {

    private JedisClient(){}

    public static Jedis jedis;

    static {
        jedis = new Jedis("192.168.187.128", 6379);

    }

    public static Jedis  getJedisClient(){
        return jedis;
    }

//    public static void main(String[] args) {
//        JedisClient.getJedisClient().set("12313" , "12313");
//        System.out.println(jedis.get("12313"));
//    }

}
