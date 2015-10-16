package com.howbuy.cc.basic.test.dubbo;

import com.howbuy.cc.basic.test.interfac.DubboCacheInterfaceTest;
import com.howbuy.cc.basic.test.model.Aaaa;
import com.howbuy.cc.basic.test.model.User;
import com.howbuy.cc.basic.test.mybatis.dao.UserCacheDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/8/20.
 */
public class DubboCacheProviderTest implements DubboCacheInterfaceTest {

    @Autowired
    private UserCacheDao userCacheDao;

    @Override
    public User getUserByCache(Integer id) {
        return userCacheDao.getUserCache(id);
    }

    @Override
    public String clearUserCacheByUserCode(Integer id) {
        userCacheDao.clearCache(id);
        return "ok";
    }

    @Override
    public String setUserCacheByUserCode(User user) {
        if(user.getUserCode() == null){
            return "fail , no is null";
        }
        userCacheDao.clearCache(user.getUserCode());
        userCacheDao.set(user);
        return "ok";
    }



    @Override
    public User getUserByCache_V2(Integer id) {
        return userCacheDao.getUserCacheV2(id);
    }

    @Override
    public String clearUserCacheByUserCode_V2(Integer id) {
        userCacheDao.clearCacheV2(id);
        return "ok";
    }

    @Override
    public String setUserCacheByUserCode_V2(User user) {
        if(user.getUserCode() == null){
            return "fail , no is null";
        }
        userCacheDao.clearCacheV2(user.getUserCode());
        userCacheDao.setV2(user);
        return "ok";
    }



    @Override
    public String setString(String text) {
        if(text == null){
            return "fail , text is null";
        }
        userCacheDao.clearString(text);
        userCacheDao.setString(text);
        return "ok";
    }


    @Override
    public String getString(String text) {
        return userCacheDao.getString(text);
    }

}
