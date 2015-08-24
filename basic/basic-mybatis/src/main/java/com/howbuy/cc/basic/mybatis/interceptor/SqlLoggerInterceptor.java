package com.howbuy.cc.basic.mybatis.interceptor;

import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.logger.CCLoggerUtil;
import com.howbuy.cc.basic.mybatis.namespace.MybatisOperationSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.Properties;

/**
 * Created by xinwei.cheng on 2015/7/28.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }) })
public class SqlLoggerInterceptor implements Interceptor , BeanFactoryAware {

    private CCLogger ccLogger = CCLogger.getLogger(this.getClass());
    private MybatisOperationSource mybatisOperationSource;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        long date = System.currentTimeMillis();
        Object obj = invocation.proceed();
        long useTime = System.currentTimeMillis() - date;

        Integer logSqlTime = mybatisOperationSource.getSqlTimeout();
        //如果没有配置长sql打印 或者超过了配置时间 打印长时间的sql
        if(logSqlTime == null || useTime > logSqlTime){
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            String sqlId = mappedStatement.getId();
            String[] sqlInfoAry = new String[3];
            sqlInfoAry[0] = sqlId;
            sqlInfoAry[1] = parameter == null ? null : Json.toJson(parameter, JsonFormat.compact());
            sqlInfoAry[2] = String.valueOf(useTime);
            ccLogger.info( "执行sql" , sqlInfoAry);
        }
        return obj;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SqlSessionTemplate sqlSessionTemplate = beanFactory.getBean(SqlSessionTemplate.class);
        sqlSessionTemplate.getConfiguration().addInterceptor(this);
        CCLoggerUtil.clearAndAddFileLog(ccLogger , mybatisOperationSource.getSqlTimeLog());
    }


    public MybatisOperationSource getMybatisOperationSource() {
        return mybatisOperationSource;
    }

    public void setMybatisOperationSource(MybatisOperationSource mybatisOperationSource) {
        this.mybatisOperationSource = mybatisOperationSource;
    }
}