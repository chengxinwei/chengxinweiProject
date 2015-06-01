import dao.HelloKittyDao;
import model.HelloKitty;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.HelloKittyService;

import java.util.Random;

/**
 * Created by xinwei.cheng on 2015/6/1.
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        HelloKittyService helloKittyService = context.getBean(HelloKittyService.class);

        HelloKitty helloKitty = new HelloKitty();
        helloKitty.setId("121");
        helloKitty.setName("helloKitt11y");
        System.out.println(helloKittyService.createHelloKitty(helloKitty));

//        HelloKittyDao dao = context.getBean(HelloKittyDao.class);
//        System.out.println(dao.get());
//
        helloKitty.setName("helloKitty" + new Random().nextInt());
        helloKittyService.updateHelloKitty(helloKitty);

    }
}
