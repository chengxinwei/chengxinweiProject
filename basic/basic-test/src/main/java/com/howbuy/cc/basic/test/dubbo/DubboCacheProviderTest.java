package com.howbuy.cc.basic.test.dubbo;

import com.howbuy.cc.basic.test.interfac.DubboCacheInterfaceTest;
import com.howbuy.cc.basic.test.model.Aaaa;
import com.howbuy.cc.basic.test.mybatis.dao.AaaaCacheDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/8/20.
 */
public class DubboCacheProviderTest implements DubboCacheInterfaceTest {

    @Autowired
    private AaaaCacheDao aaaaCacheDao;

    @Override
    public List<Aaaa> getAaaaByCache(Integer id) {
        return aaaaCacheDao.getAaaaCache(id);
    }

    @Override
    public String clearAaaaCache(Integer id) {
        aaaaCacheDao.clearCache(id);
        return "ok";
    }

    @Override
    public String setCache(Aaaa aaaa) {
        if(aaaa.getNo() == null){
            return "fail , no is null";
        }
        aaaaCacheDao.clearCache(aaaa.getNo());
        aaaaCacheDao.set(aaaa);
        return "ok";
    }


}
