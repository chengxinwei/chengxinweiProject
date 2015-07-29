package com.howbuy.cc.basic.mybatis.interceptor;

import com.howbuy.cc.basic.logger.CCLogger;
import com.howbuy.cc.basic.mybatis.constant.MyBatisConstant;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Date;
import java.util.Properties;

/**
 * Created by xinwei.cheng on 2015/7/28.
 */
@Intercepts({@Signature(type=StatementHandler.class , method="prepare" , args={Connection.class})})
public class SqlLoggerInterceptor implements Interceptor {

    CCLogger ccLogger = CCLogger.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Date date = new Date();


        String[] sqlInfoAry = new String[3];
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            BoundSql boundSql = statementHandler.getBoundSql();
            sqlInfoAry[0] = boundSql.getSql().toString();
        }
        Object obj = invocation.proceed();
        long useTime = new Date().getTime() - date.getTime();
        sqlInfoAry[1] = String.valueOf(useTime);
        ccLogger.info(MyBatisConstant.SQL_TIME , "执行sql" , sqlInfoAry);
        return obj;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}