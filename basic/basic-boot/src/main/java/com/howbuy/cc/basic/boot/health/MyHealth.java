//package com.howbuy.cc.basic.boot.health;
//
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//
///**
// * Created by xinwei.cheng on 2015/9/7.
// */
//@Component
//public class MyHealth implements HealthIndicator {
//
//    @Override
//    public Health health() {
//        return Health.down().withException(new RuntimeException("test")).withDetail("Error Code", 12).build();
//    }
//}
