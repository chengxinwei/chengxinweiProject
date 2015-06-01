import model.HelloKitty;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.HelloKittyService;

/**
 * Created by xinwei.cheng on 2015/6/1.
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        HelloKittyService helloKittyService = context.getBean(HelloKittyService.class);

        HelloKitty helloKitty = new HelloKitty();
        helloKitty.setId("12");
        helloKitty.setName("helloKitty");
        System.out.println(helloKittyService.createHelloKitty(helloKitty));

    }
}
