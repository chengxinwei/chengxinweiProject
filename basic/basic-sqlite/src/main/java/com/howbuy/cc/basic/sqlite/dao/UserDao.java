package com.howbuy.cc.basic.sqlite.dao;

import com.howbuy.cc.basic.mybatis.dao.MybatisCommonDao;
import com.howbuy.cc.basic.mybatis.dao.callback.ExecuteCallBack;
import com.howbuy.cc.basic.sqlite.model.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/8/24.
 */
@Repository
public class UserDao extends MybatisCommonDao<User>{

    public void insert(User user){
        super.insert("insert" , user);
    }

    public List<User> selectList(){
        return super.selectList("select" , null);
    }

    public void create(){
        super.execute("create", new ExecuteCallBack<Void>() {
            public Void execute(String fullSqlId, SqlSessionTemplate sqlSessionTemplate) {
                sqlSessionTemplate.selectOne("create");
                return null;
            }
        });
    }
}
