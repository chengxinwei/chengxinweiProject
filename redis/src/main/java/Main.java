import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserService;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/5/29.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"spring-cache.xml" , "applicationHttpProvider.xml"});

        UserService userService = context.getBean(UserService.class);


        System.out.println(userService.getUser(8000));

        System.in.read();

    }

}
