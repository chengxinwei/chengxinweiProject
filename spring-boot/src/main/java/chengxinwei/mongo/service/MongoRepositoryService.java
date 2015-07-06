package chengxinwei.mongo.service;

import chengxinwei.mongo.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xinwei.cheng on 2015/7/1.
 */
public interface MongoRepositoryService extends CrudRepository<City, Long> {


    Page<City> findAll(Pageable pageable);

    City findByNameAllIgnoringCase(String name);


}
