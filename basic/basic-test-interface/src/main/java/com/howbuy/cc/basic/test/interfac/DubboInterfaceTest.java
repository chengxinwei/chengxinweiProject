package com.howbuy.cc.basic.test.interfac;

import com.howbuy.cc.basic.test.model.Aaaa;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public interface DubboInterfaceTest {

    public List<Aaaa> getAaaaByDb(Integer id);

    public List<Aaaa> getAaaaByCache(Integer id);

    public Aaaa insetAaaa(Aaaa aaaa);

    public Aaaa updateAaaa(Aaaa aaaa);

    public String deleteAaaa(Integer id);

    public String clearAaaaCache(Integer id);

    public String set(Aaaa aaaa);

    public String ping();

    public void exception();
}
