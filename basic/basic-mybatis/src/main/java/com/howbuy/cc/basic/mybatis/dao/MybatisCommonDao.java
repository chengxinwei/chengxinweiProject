package com.howbuy.cc.basic.mybatis.dao;

import com.howbuy.cc.basic.mybatis.dao.callback.ExecuteCallBack;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * mybatis基本基本dao层
 * Created by xinwei.cheng on 2015/6/3.
 */
@SuppressWarnings("unused")
public class MybatisCommonDao<T>{

    @Autowired
    private SqlSessionTemplate sqlSession;

    private final Class<T> clazz;

    private  String nameSpace;

    public MybatisCommonDao(){
        clazz = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        nameSpace  = clazz.getSimpleName() + "Mapper";
    }


    /**
     * 插入
     * @param t 插入的对象
     */
    public int insert(T t){
        return sqlSession.insert(nameSpace + ".insert" , t);
    }


    /**
     * 查询一个
     * @param params 查询的参数
     */
    public T selectOne(Map<String,Object> params){
        return sqlSession.selectOne(nameSpace  + ".selectOne", params);
    }


    /**
     * 查询列表
     * @param params 查询的参数
     * @return 返回对象LIST
     */
    public List<T> selectList(Map<String,Object> params){
        return sqlSession.selectList(nameSpace + ".selectList", params);
    }


    /**
     * 分页
     * @param params 查询的参数
     * @param pageNo 第几页
     * @param pageSize 每页多少条
     * @param orderby 排序
     * @return 分页对象
     */
    public Page<T> page(Map<String,Object> params , Integer pageNo , Integer pageSize , String orderby){

        int count = this.count(params);
        Page<T> page = new Page<>(pageSize , pageNo , count);
        if(count == 0 ){
            page.setPageList(new ArrayList<T>());
            return page;
        }

        params.put("beginNum" , page.getBeginNum());
        params.put("endNum" ,  page.getEndNum());
        params.put("orderby" , orderby);
        List<T> list = sqlSession.selectList(nameSpace  + ".selectList", params);
        page.setPageList(list);
        return page;
    }

    /**
     * 查询数量
     * @param params 查询条件
     * @return 数量
     */
    public int count(Map<String,Object> params){
        return sqlSession.selectOne(nameSpace  + ".count" , params);
    }


    /**
     * 更新
     * @param t 更新的对象
     */
    public int update(T t){
        return sqlSession.update(nameSpace + ".update", t);
    }


    /**
     * 执行某一个特定的方法
     * @param executeCallBack 执行的接口函数
     * @param <E>   泛型类
     * @return 返回数据
     */
    public <E> E execute(ExecuteCallBack<E> executeCallBack){
        return executeCallBack.execute(sqlSession);
    }

}
