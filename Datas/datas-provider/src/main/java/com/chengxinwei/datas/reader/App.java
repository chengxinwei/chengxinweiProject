package com.chengxinwei.datas.reader;

import com.chengxinwei.datas.model.Datas;
import com.chengxinwei.datas.model.DbType;
import com.chengxinwei.datas.reader.handler.DataReaderHandler;
import com.howbuy.cc.basic.spring.SpringBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws InterruptedException {

        new ClassPathXmlApplicationContext("classpath:application.xml");

        Datas datas = new Datas();
        datas.setTableName("aaaa");
        datas.setToDbType(DbType.mongodb);

        DataReaderHandler dataReaderHandler = SpringBean.getBean(DataReaderHandler.class);
        dataReaderHandler.readData(datas);

        Thread.sleep(10*10000);
    }
}
