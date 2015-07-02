package chengxinwei.mongo.listener;

import chengxinwei.mongo.model.City;
import chengxinwei.mongo.service.MongoRepositoryService;
import chengxinwei.mongo.service.MongoTemplateService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by xinwei.cheng on 2015/7/2.
 */
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        MongoTemplateService mongoService = event.getApplicationContext().getBean(MongoTemplateService.class);
        MongoRepositoryService mongoRepositoryService = event.getApplicationContext().getBean(MongoRepositoryService.class);
//        City city = new City("chengxin" , "1");
//        city.setId(1L);
//        mongoService.save(city);

        System.out.println(mongoRepositoryService.findByNameAllIgnoringCase("chengxin"));


    }
}