package com.howbuy.cc.basic.mybatis.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.howbuy.cc.basic.mybatis.annotation.CCDatasourceRoute;
import com.howbuy.cc.basic.mybatis.model.Page;
import com.howbuy.cc.basic.mybatis.util.RouteBeanNameGenerator;
import com.howbuy.cc.basic.spring.SpringBean;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/6/3.
 */
public class MybatisCommonDao<T>{

    protected SqlSessionTemplate sqlSession;

    private final Class<T> clazz;

    private  String nameSpace;

    public MybatisCommonDao(){
        clazz = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        nameSpace  = clazz.getSimpleName() + "Mapper";
    }


    /**
     * 插入
     * @param t
     */
    public void insert(T t){
        sqlSession.insert(nameSpace + ".insert" , t);
    }


    /**
     * 查询一个
     * @param params
     */
    public void selectOne(Map<String,Object> params){
        sqlSession.insert(nameSpace  + ".selectOne", params);
    }


    /**
     * 查询列表
     * @param params
     * @return
     */
    public List<T> selectList(Map<String,Object> params){
        return sqlSession.selectList(nameSpace + ".selectList", params);
    }


    /**
     * 分页
     * @param params
     * @param pageNo
     * @param pageSize
     * @param orderby
     * @return
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
     * 分页
     * @param params
     * @param pageNo
     * @param pageSize
     * @param orderby
     * @return
     */
    public Page<T> page(Map<String,Object> params , Integer pageNo , Integer pageSize , Integer count , String orderby ){

        Page<T> page = new Page<>(pageSize , pageNo , count);

        if(pageNo > page.getTotalPage()){
            page.setPageList(new ArrayList<T>());
            return page;
        }

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
     * @param params
     * @return
     */
    public int count(Map<String,Object> params){
        return sqlSession.selectOne(nameSpace  + ".count" , params);
    }


    /**
     * 更新
     * @param t
     */
    public void update(T t){
        sqlSession.insert(nameSpace  + ".update", t);
    }



//    @PostConstruct
//    public void init() throws Exception {
//        CCDatasourceRoute ccDatasourceRoute = this.getClass().getAnnotation(CCDatasourceRoute.class);
//        if(ccDatasourceRoute != null){
//            DruidDataSource datasource = SpringBean.getBean(ccDatasourceRoute.value() , DruidDataSource.class);
//            if(datasource == null){
//                throw new BeanCreationException("can not find bean for bean name :"+ ccDatasourceRoute.value() + ", class " + DruidDataSource.class);
//            }
//            SqlSessionFactoryBean sqlSessionFactoryBean =
//                    SpringBean.getBean(RouteBeanNameGenerator.getBeanName(ccDatasourceRoute.value()) , SqlSessionFactoryBean.class);
//            this.sqlSession = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
//        }
//    }
}
