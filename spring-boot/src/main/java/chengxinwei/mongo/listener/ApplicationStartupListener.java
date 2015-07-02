package chengxinwei.mongo.listener;

import chengxinwei.mongo.mongo.City;
import chengxinwei.mongo.service.MongoService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by xinwei.cheng on 2015/7/2.
 */
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MongoService mongoService = event.getApplicationContext().getBean(MongoService.class);

        City city = new City("chengxin" , "1");
        city.setId(1L);
        mongoService.insert(city);

        System.out.println(mongoService.findByNameAllIgnoringCase("chengxin"));


    }
}