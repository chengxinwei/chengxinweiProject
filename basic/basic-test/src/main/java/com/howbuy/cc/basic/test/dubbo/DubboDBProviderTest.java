package com.howbuy.cc.basic.test.dubbo;

import com.howbuy.cc.basic.test.interfac.DubboDBInterfaceTest;
import com.howbuy.cc.basic.test.mybatis.dao.AaaaDao;
import com.howbuy.cc.basic.test.model.Aaaa;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public class DubboDBProviderTest implements DubboDBInterfaceTest {

    @Autowired
    private AaaaDao aaaaDao;

    @Override
    public List<Aaaa> getAaaaByDb(Integer id) {
        return aaaaDao.getAaaa(id);
    }

    @Override
    public String insetAaaaByDb(Aaaa aaaa) {
        aaaaDao.insert(aaaa);
        return "ok";
    }

    @Override
    public String updateAaaaByDb(Aaaa aaaa) {
        aaaaDao.update(aaaa);
        return "ok";
    }

    @Override
    public String deleteAaaaByDb(Integer id) {
        aaaaDao.delete(id);
        return "ok";
    }


}
