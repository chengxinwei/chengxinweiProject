package chengxinwei.datas;

import chengxinwei.datas.reader.handler.DataReaderHandler;
import com.howbuy.cc.basic.spring.SpringBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws InterruptedException {

        new ClassPathXmlApplicationContext("classpath:application.xml");


        final String tableName = "aaaa";

        SpringBean.getBean(DataReaderHandler.class).readData(tableName);
    }
}
