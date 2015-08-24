package com.howbuy.cc.basic.test.interfac;

import com.howbuy.cc.basic.test.model.Aaaa;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public interface DubboCacheInterfaceTest {

    public List<Aaaa> getAaaaByCache(Integer id);

    public String clearAaaaCache(Integer id);

    public String setCache(Aaaa aaaa);

}
