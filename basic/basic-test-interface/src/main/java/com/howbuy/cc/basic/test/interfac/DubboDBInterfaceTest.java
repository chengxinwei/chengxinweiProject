package com.howbuy.cc.basic.test.interfac;

import com.howbuy.cc.basic.test.model.Aaaa;

import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/27.
 */
public interface DubboDBInterfaceTest {

    public List<Aaaa> getAaaaByDb(Integer id);

    public String insetAaaaByDb(Aaaa aaaa);

    public String updateAaaaByDb(Aaaa aaaa);

    public String deleteAaaaByDb(Integer id);

}
