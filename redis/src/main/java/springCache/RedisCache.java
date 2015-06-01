package springCache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import redis.JedisClient;
import util.SerializationUtil;

/**
 * Created by xinwei.cheng on 2015/5/29.
 */

public class RedisCache implements Cache {


    public String getName() {
        return "demoCache";
    }

    public Object getNativeCache() {
        return JedisClient.getJedisClient();
    }

    public ValueWrapper get(Object key) {
        Object obj = SerializationUtil.deserialize(JedisClient.getJedisClient().get(getKey(key)));
        if (obj == null) {
            return null;
        }
        return new SimpleValueWrapper(obj);
    }

    public <T> T get(Object key, Class<T> type) {
        throw new RuntimeException("not support");
    }

    public void put(Object key, Object value) {
        JedisClient.getJedisClient().setex(getKey(key), 1000, SerializationUtil.serialize(value));
    }

    public void evict(Object key) {
        JedisClient.getJedisClient().del(getKey(key));
    }

    public void clear() {
        throw new RuntimeException("not support");
    }


    private byte[] getKey(Object key) {
        return key.toString().getBytes();
    }
}
