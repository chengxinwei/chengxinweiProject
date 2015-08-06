使用文档

basic-cache 是基于PA的cache-client + spring + redis + ehcache 的应用框架，开发basic-cache的核心目的是易用,添加注解的方式使用缓存


一、使用方式
    1.  引入basic-cache
          <dependency>
             <groupId>com.howbuy.cc.basic</groupId>
             <artifactId>basic-mybatis</artifactId>
             <version>1.0.0</version>
          </dependency>

二、功能点的使用
    1.  动态数据源切换
        1）数据源配置 ， 使用mybatis的DynamicDataSource 数据路由
        <bean id="dataSource" class="com.howbuy.cc.basic.mybatis.datasourceRoute.DynamicDataSource">
            <property name="targetDataSources" >
                <map>
                    <entry key="master" value-ref="master"></entry>
                    <entry key="slave" value-ref="slave"></entry>
                </map>
            </property>
            <!-- defaultTargetDataSource 默认数据源 -->
            <property name="defaultTargetDataSource" ref="master"></property>
        </bean>

        <bean id="master" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
            <property name="driverClassName" value="${jdbc.driver}"/>
            <property name="url" value="${jdbc.url}"/>
            <property name="username" value="${jdbc.username}"/>
            <property name="password" value="${jdbc.password}"/>
            <property name="initialSize" value="${jdbc.initialSize}"/>
            <property name="maxActive" value="${jdbc.maxActive}"/>
            <property name="minIdle" value="${jdbc.minIdle}"/>
            <property name="validationQuery" value="select 1 from dual"/>
        </bean>


        <bean id="slave" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
            <property name="driverClassName" value="${jdbc.slave.driver}"/>
            <property name="url" value="${jdbc.slave.url}"/>
            <property name="username" value="${jdbc.slave.username}"/>
            <property name="password" value="${jdbc.slave.password}"/>
            <property name="initialSize" value="${jdbc.slave.initialSize}"/>
            <property name="maxActive" value="${jdbc.slave.maxActive}"/>
            <property name="minIdle" value="${jdbc.slave.minIdle}"/>
            <property name="validationQuery" value="select 1 from dual"/>
        </bean>

        2）在dao类上添加注解 @CCDatasourceRoute 不写为默认defaultTargetDataSource的配置

    2.  基础的增删改查
        1）基础dao继承 MybatisCommonDao 内部提供基础的增删改查
        2）所有的增删改查方法为protected的方法，控制访问级别只能是子类访问
        3）使用demo如下

        @Repository
        public class CustInfoMasterDao extends MybatisCommonDao<CustInfo>{

            public CustInfo selectOne(){
                return super.selectOne("selectOne" , null);
            }

        }

    3.  分页，分页使用mybatis的include功能实现
        1）配置扫描添加 basic/mapper/*Mapper.xml
            如：
            <property name="mapperLocations" >
              <list>
                  <value>classpath:basic/mapper/*Mapper.xml</value>
              </list>
          </property>
        2）在原本的list 方法上include  commonMapper.Pagination_Head 和 commonMapper.Pagination_Foot
            如:
              <include refid="commonMapper.Pagination_Head"></include>
                select * from cm_announce
              <include refid="commonMapper.Pagination_Foot"></include>
        3)分页对象为 com.howbuy.cc.basic.mybatis.model.Page 对象
            1）内部提供了是否第一页，是否最后一页，根据当前页数和每页多少条转换开始和结束记录数
            2）计算了如果有超出则会获取最后一页
        4）调用 page 方法即可获取分页

    4.  sql日志记录
        1）添加日志拦截器 sqlLoggerInterceptor
            如：
             <property name="plugins">
                <list>
                    <ref bean="sqlLoggerInterceptor"></ref>
                </list>
             </property>
        2）添加log4j的配置文件
            log4j.logger.com.howbuy.cc.basic.mybatis.interceptor.SqlLoggerInterceptor = info,sqlTime
            log4j.additivity.com.howbuy.cc.basic.mybatis.interceptor.SqlLoggerInterceptor = false
            log4j.appender.sqlTime=org.apache.log4j.DailyRollingFileAppender
            log4j.appender.sqlTime.File=log/sqlTime.log
            log4j.appender.sqlTime.Append = true
            log4j.appender.sqlTime.layout=org.apache.log4j.PatternLayout
            log4j.appender.sqlTime.layout.ConversionPattern= %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n

            日志demo如下：
            2015-08-04 11:01:16  [ main:18070 ] - [ INFO ]  sql_time|192.168.121.199,192.168.61.1,192.168.187.1|base-mybatis|执行sql|com.howbuy.cc.basic.mybatis.dao.CustInfoMasterDao.delete|1|14931

        3）新增参数 mybatis.sql.log.time 用来设置超长时间的sql打印，如果设置了mybatis.sql.log.time，超过 mybatis.sql.log.time 的sql才会打印
    5.  mybatis 的命名空间默认为当前类的包名+类名 如 com.howbuy.cc.basic.TestDao
        若需要自己制定命名空间，使用CCNamespace注解自定义