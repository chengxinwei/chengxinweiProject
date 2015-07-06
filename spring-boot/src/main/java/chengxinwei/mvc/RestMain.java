package chengxinwei.mvc;

import chengxinwei.mongo.listener.ApplicationStartupListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by xinwei.cheng on 2015/7/1.
 */

@SpringBootApplication
public class RestMain {

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(RestMain.class);
        springApplication.run(args);
    }




}
