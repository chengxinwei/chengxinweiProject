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
        1）可以使用 Configuration.get("default.application.name") 获取
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

