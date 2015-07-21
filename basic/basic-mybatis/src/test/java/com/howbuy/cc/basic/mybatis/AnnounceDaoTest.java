package com.howbuy.cc.basic.mybatis;

import com.howbuy.cc.basic.mybatis.dao.AnnounceDao;
import com.howbuy.cc.basic.mybatis.model.Announce;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/7/20.
 */
public class AnnounceDaoTest extends BaseTest{


    @Autowired
    private AnnounceDao announceDao;


    @Test
    public void update(){
        List<Announce> announceList = new ArrayList<>();
        Announce announce = new Announce();
        announce.setAnnouncedid(221);
        announce.setSubject("1subject");

        Announce announce1 = new Announce();
        announce1.setAnnouncedid(206);
        announce1.setSubject("2subject");

        announceList.add(announce);
        announceList.add(announce1);

        announceDao.batchUpdate(announceList);
    }

}
