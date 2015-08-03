package chengxinwei.datas.reader;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws InterruptedException {

        new ClassPathXmlApplicationContext("classpath:application.xml");

        Thread.sleep(10*10000);
    }
}
