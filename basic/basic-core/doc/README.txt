使用文档

basic-core 是基于spring + dubbo的应用框架，开发basic-core的核心目的是易用，扩展


一、使用方式
    1.  引入basic-core
         <dependency>
            <artifactId>basic-core</artifactId>
            <version>1.0.0</version>
            <packaging>jar</packaging>
         </dependency>
    2.  添加 default.application.name 在properties 中
    3.  引入配置文件 <import resource="classpath:basic/spring/base-spring.xml"></import>

二、功能点以及相关使用
    1.  配置文件读取，默认加载classpath下所有以properties结尾的配置文件。
        读取jar包当前目录的 conf 文件夹，读取目录下所有properties结尾的配置文件，若有重复则覆盖原有的配置
        1）可以使用
            Configuration.get("default.application.name") 获取
            Configuration.getInt(key) , Configuration.getBoolean(key) 获取不同的类型
        2）可以使用 spring @Value("${testValue}") 注入
        *参考 test.java.com.howbuy.cc.basic.configuration 下的测试类

    2.  SpringBean 静态方法直接获取bean
        *参考 test.java.com.howbuy.cc.basic.spring 下的测试类

    3.  工具包：
        Json转换的nutz，fastjson
        时间转换的 joda
        apache-common-lang3
        机器IP的工具类IpUtil
        序列化工具类SerializationUtil
        *参考开源官方文档

    4.  日志打印 CCLogger
        1）通过  CCLogger ccLogger = CCLogger.getLogger(this.getClass()); 获取 CCLogger
        2）调用同 log4j 一样
            cclogger.info(code , message , args...)
        3) CCLogger 会默认打印当前机器的ip 当前的应用名，若想独立设置日志的应用名，在properties中添加logger.application.name
        4）范例：code|192.168.121.199,192.168.61.1,192.168.187.1|logger-base-core|message|
        *参考 test.java.com.howbuy.cc.basic.logger 下的测试类
        5）基于dubbo请求自动生成code ，code 自动生成规则 dubbo 请求的类名+方法名
           dubbo 的 filter 添加 dubboCodeLoggerFilter
           如：<dubbo:service application="basic-dubbo-test-provider"
                     interface="com.howbuy.cc.basic.dubbo.DubboInterfaceTest"
                     ref="dubboProviderTest"
                     filter="dubboCodeLoggerFilter"/>


    5.  DUBBO 请求日志,记录的dubbo每次访问的请求的类，请求的方法，请求参数，响应值，请求耗时
        1）dubbo 的 filter 添加 dubboAccessLoggerFilter
        如： <dubbo:service application="basic-dubbo-test-provider"
                interface="com.howbuy.cc.basic.dubbo.DubboInterfaceTest"
                ref="dubboProviderTest"
                filter="dubboAccessLoggerFilter"/>

        2）在log4j中添加如下配置
            log4j.logger.com.howbuy.cc.basic.filter.DubboAccessLoggerFilter = info,access
            log4j.additivity.com.howbuy.cc.basic.filter.DubboAccessLoggerFilter = false
            log4j.appender.access=org.apache.log4j.DailyRollingFileAppender
            log4j.appender.access.File=log/access.log
            log4j.appender.access.Append = true
            log4j.appender.access.layout=org.apache.log4j.PatternLayout
            log4j.appender.access.layout.ConversionPattern= %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n

         *参考 test.java.com.howbuy.cc.basic.dubbo 下的测试类
         *以及 test.resources 下的log4j配置

2015-08-12
    1. cclogger 新增字符串的构造函数
    2. 日志记录区分了access和request日志 request的filter名字 dubboRequestLoggerFilter
    3. 日志代码优化，抽离了公共的方法
    4. 错误日志以warn形式输出，成功日志以info形式输出
        request demo：
        log4j.logger.com.howbuy.cc.basic.filter.DubboRequestLoggerFilter = info,request
        log4j.additivity.com.howbuy.cc.basic.filter.DubboRequestLoggerFilter = false
        log4j.appender.request=org.apache.log4j.DailyRollingFileAppender
        log4j.appender.request.File=log/request.log
        log4j.appender.request.Append = true
        log4j.appender.request.layout=org.apache.log4j.PatternLayout
        log4j.appender.request.layout.ConversionPattern= %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n

        范例：
        2015-08-12 10:20:00  [ main:3075 ] - [ INFO ]  DubboInterfaceTest.getDate|2bcda6e7-b17c-4f03-9c42-2adfa4139cd2|192.168.121.199,192.168.61.1,192.168.187.1|logger-base-core|request.success|DubboInterfaceTest|getDate|[]|"2015-08-12 10:20:00"|352
        2015-08-12 10:20:47  [ main:2342 ] - [ WARN ]  DubboInterfaceTest.exception|d846cb53-568c-42c4-a4c4-7f02366b189c|192.168.121.199,192.168.61.1,192.168.187.1|logger-base-core|request.fail|DubboInterfaceTest|exception|[]|java.lang.NullPointerException: testException|381
        2015-08-12 10:20:48  [ main:2345 ] - [ INFO ]  DubboInterfaceTest.getDate|d846cb53-568c-42c4-a4c4-7f02366b189c|192.168.121.199,192.168.61.1,192.168.187.1|logger-base-core|request.success|DubboInterfaceTest|getDate|[]|"2015-08-12 10:20:47"|2


    5. 新增驱动 <basic-core:core-driven/>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns:basic-core="http://com.howbuy.cc.basic/schema/basic-core"
               xsi:schemaLocation="http://www.springframework.org/schema/beans
                http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
                http://com.howbuy.cc.basic/schema/basic-core
                http://com.howbuy.cc.basic/schema/basic-core.xsd">
