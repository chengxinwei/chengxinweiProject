package com.howbuy.cc.basic.cache;

import com.howbuy.cc.basic.cache.client.EhCacheClient;
import com.howbuy.cc.basic.cache.common.BaseTest;
import com.howbuy.cc.basic.cache.model.User;
import com.howbuy.cc.basic.cache.service.EhService;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.spring.SpringBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xinwei.cheng on 2015/7/6.
 */
public class EhCacheTest extends BaseTest {

    @Autowired
    private EhService ehService;

    @Test
    public void getEhcacheTest(){
        User user1 = ehService.getUser(1);
        User user2 = ehService.getUser(1);
        Assert.assertEquals(user1, user2);
    }

    @Test
    public void ehcacheTimeoutTest(){
        User user1 =  ehService.getUser5Sec(1);
        try {
            Thread.sleep(5100);
        } catch (InterruptedException e) {
        }
        User user2 = ehService.getUser5Sec(1);
        Assert.assertNotEquals(user1, user2);
    }

    @Test
    public void ehcacheClearTest(){
        User user1 = ehService.getUser(1);
        ehService.clearUser(1);
        User user2 = ehService.getUser(1);
        Assert.assertNotEquals(user1, user2);
    }

    @Test
    public void ehcacheAPITest(){
        User user1 = ehService.getUser(1);
        User user2 = (User) SpringBean.getBean(EhCacheClient.class).get("User.getUser" , "1");
        Assert.assertEquals(user1, user2);
    }

    @Test
    public void ehcacheMaxElement(){
        int maxElementCount = Configuration.getInt("ehcache.maxElementCount");
        EhCacheClient ehCacheClient = SpringBean.getBean(EhCacheClient.class);
        for(int i = 0 ; i <= maxElementCount ; i++) {
            ehCacheClient.put("ehcache.test.maxElement" , String.valueOf(i) , new User() , 30 );
        }
        Assert.assertNull(ehCacheClient.get("ehcache.test.maxElement", "0"));
        Assert.assertNotNull(ehCacheClient.get("ehcache.test.maxElement", "2"));
    }

    @Test
    public void ehcacheNull(){
        ehService.getNull(true);
        Assert.assertNotNull(ehService.getNull(false));
    }


    @Test
    public void testLog(){
        for(int i = 0 ; i < 10 ; i ++){
            ehService.getUser(i%5);
        }
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
