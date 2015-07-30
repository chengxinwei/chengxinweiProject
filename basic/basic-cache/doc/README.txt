使用文档

basic-cache 是基于PA的cache-client + spring + redis + ehcache 的应用框架，开发basic-cache的核心目的是易用,添加注解的方式使用缓存


一、使用方式
    1.  引入basic-cache
          <dependency>
             <groupId>com.howbuy.cc.basic</groupId>
             <artifactId>basic-cache</artifactId>
             <version>1.0.0</version>
          </dependency>
    2.  引入配置文件 <import resource="classpath:basic/spring/cache/spring-cache.xml"></import>

二、功能点的使用
    1.  缓存 在方法上添加注解 @Cacheable
        value ： REDIS_CACHE_1D / REDIS_CACHE_1H / EH_CACHE_1D / EH_CACHE_1H.....
                分别对应 redis 和 ehcache 不同的超时时间
                *参考com.howbuy.cc.basic.cache.constant.CacheConstant
        key ： SPEL表达式
                *参考官方文档
                完整的key是 ${cache.application.name} + key
                若没有配置cache.application.name 则自动获取default.application.name
    2.  清除缓存 在方法上添加注释 @CacheEvict
        value : 同 @Cacheable
        key   : 同 @Cacheable

    3.  若要使用 redis 请在配置文件中配置
        redis.sentinel.path=${sentinel.ip}
        redis.sentinel.serverName=${sentinel.serverName}