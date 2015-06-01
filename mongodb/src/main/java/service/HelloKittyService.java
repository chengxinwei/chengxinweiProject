package service; /**
 *
 */

import dao.HelloKittyDao;
import model.HelloKitty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloKittyService {

    @Autowired
    private HelloKittyDao helloKittyDAO;

    public HelloKitty createHelloKitty(HelloKitty hello) {
        helloKittyDAO.createHelloKitty(hello);

        HelloKitty ret = helloKittyDAO.getHelloKittyByName(hello.getName());


        return ret;
    }


    public void updateHelloKitty(HelloKitty hello){
        helloKittyDAO.updateHelloKitty(hello);

//        HelloKitty ret = helloKittyDAO.getHelloKittyByNameWithCollection(hello.getName());


//        return ret;
    }


}