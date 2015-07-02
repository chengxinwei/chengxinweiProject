package chengxinwei.mongo.service;

import chengxinwei.mongo.mongo.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by xinwei.cheng on 2015/7/1.
 */
public interface MongoService extends MongoRepository<City, Long> {

    Page<City> findAll(Pageable pageable);

    City findByNameAllIgnoringCase(String name);


}
