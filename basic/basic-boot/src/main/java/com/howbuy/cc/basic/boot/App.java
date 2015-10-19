package com.howbuy.cc.basic.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by xinwei.cheng on 2015/9/7.
 */
@SpringBootApplication
@ImportResource("spring.xml")
public class App {

    public static void main(String[] args) throws Exception {
        start2(args);
    }


    public static void start1(String[] args){
        SpringApplication.run(App.class, args);
    }

    public static void start2(String[] args){
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }

}
