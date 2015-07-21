package com.howbuy.cc.basic.mybatis.dao;

import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.annotation.CCNameSpaceMapper;
import com.howbuy.cc.basic.mybatis.dao.callback.ExecuteCallBack;
import com.howbuy.cc.basic.mybatis.datasourceRoute.DynamicDataSourceSwitch;
import com.howbuy.cc.basic.mybatis.model.Page;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    public MybatisCommonDao() {
        ParameterizedType parameterizedType = null;
        Class _clazz =  getClass();
        while (true) {
            Type type = _clazz.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType)type;
                break;
            }
            _clazz = _clazz.getSuperclass();
        }
        clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        nameSpace = getNameSpace();
    }


    /**
     * 获取命名空间
     * @return
     */
    public String getNameSpace(){
        CCNameSpaceMapper ccMapper = this.getClass().getAnnotation(CCNameSpaceMapper.class);
        return ccMapper == null ?  clazz.getSimpleName() + "Mapper" : ccMapper.value();
    }


    /**
     * 插入
     * @param t 插入的对象
     */
    public int insert(final T t){
        return execute(new ExecuteCallBack<Integer>(){
            @Override
            public Integer execute(String nameSpace, SqlSessionTemplate sqlSessionTemplate) {
                return sqlSessionTemplate.insert(nameSpace + ".insert", t);
            }
        });
    }


    /**
     * 查询一个
     * @param params 查询的参数
     */
    public T selectOne(final Map<String,Object> params){
        return execute(new ExecuteCallBack<T>(){
            @Override
            public T execute(String nameSpace, SqlSessionTemplate sqlSessionTemplate) {
                return sqlSessionTemplate.selectOne(nameSpace + ".selectOne", params);
            }
        });

    }


    /**
     * 查询列表
     * @param params 查询的参数
     * @return 返回对象LIST
     */
    public List<T> selectList(final Map<String,Object> params){
        return execute(new ExecuteCallBack<List<T>>(){
            @Override
            public List<T> execute(String nameSpace, SqlSessionTemplate sqlSessionTemplate) {
                return sqlSessionTemplate.selectList(nameSpace + ".selectList", params);
            }
        });

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
        List<T> list = this.selectList(params);
        page.setPageList(list);
        return page;
    }

    /**
     * 查询数量
     * @param params 查询条件
     * @return 数量
     */
    public int count(final Map<String,Object> params){
        return execute(new ExecuteCallBack<Integer>(){
            @Override
            public Integer execute(String nameSpace, SqlSessionTemplate sqlSessionTemplate) {
                return sqlSessionTemplate.selectOne(nameSpace  + ".count" , params);
            }
        });
    }


    /**
     * 更新
     * @param t 更新的对象
     */
    public int update(final T t){
        return execute(new ExecuteCallBack<Integer>(){
            @Override
            public Integer execute(String nameSpace, SqlSessionTemplate sqlSessionTemplate) {
                return sqlSessionTemplate.update(nameSpace + ".update", t);
            }
        });
    }


    /**
     * 执行某一个特定的方法
     * @param executeCallBack 执行的接口函数
     * @param <E>   泛型类
     * @return 返回数据
     */
    protected  <E> E execute(ExecuteCallBack<E> executeCallBack){
        Class<?> clazz = this.getClass();
        if(clazz.isAnnotationPresent(CCDatasourceRoute.class)){
            CCDatasourceRoute ccDatasourceRoute = clazz.getAnnotation(CCDatasourceRoute.class);
            String datasourceName = ccDatasourceRoute.value();
            DynamicDataSourceSwitch.setDataSource(datasourceName);
        }
        return executeCallBack.execute(this.nameSpace , sqlSession);
    }

}
