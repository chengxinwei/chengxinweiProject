package com.howbuy.cc.basic.test.dubbo;

import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.test.mybatis.dao.AaaaDao;
import com.howbuy.cc.basic.test.interfac.DubboInterfaceTest;
import com.howbuy.cc.basic.test.model.Aaaa;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public class DubboProviderTest implements DubboInterfaceTest {

    @Autowired
    private AaaaDao aaaaDao;


    @Override
    public List<Aaaa> getAaaaByDb(Integer id) {
        return aaaaDao.getAaaa(id);
    }

    @Override
    public List<Aaaa> getAaaaByCache(Integer id) {
        return aaaaDao.getAaaaCache(id);
    }

    @Override
    public Aaaa insetAaaa(Aaaa aaaa) {
        aaaaDao.insert(aaaa);
        return aaaa;
    }

    @Override
    public Aaaa updateAaaa(Aaaa aaaa) {
        aaaaDao.update(aaaa);
        return aaaa;
    }

    @Override
    public String deleteAaaa(Integer id) {
        aaaaDao.delete(id);
        return "ok";
    }

    @Override
    public String clearAaaaCache(Integer id) {
        aaaaDao.clearCache(id);
        return "ok";
    }

    @Override
    public String set(Aaaa aaaa) {
        aaaaDao.set(aaaa);
        return "ok";
    }

    @Override
    public String ping() {
        return "ok";
    }

    @Override
    public void exception() {
        throw new NullPointerException("testException");
    }
}
