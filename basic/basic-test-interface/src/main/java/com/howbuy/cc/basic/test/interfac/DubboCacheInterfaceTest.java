package com.howbuy.cc.basic.test.interfac;

import com.howbuy.cc.basic.test.model.Aaaa;
import com.howbuy.cc.basic.test.model.User;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public interface DubboCacheInterfaceTest {

    public User getUserByCache(Integer id);

    public String clearUserCacheByUserCode(Integer id);

    public String setUserCacheByUserCode(User user);


    public User getUserByCache_V2(Integer id);

    public String clearUserCacheByUserCode_V2(Integer id);

    public String setUserCacheByUserCode_V2(User user);


    public String setString(String text) ;

    public String getString(String text);

}
