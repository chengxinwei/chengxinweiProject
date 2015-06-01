package service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import model.User;
import org.springframework.cache.annotation.Cacheable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by xinwei.cheng on 2015/5/29.
 */
@Path("user")
@Produces({ContentType.APPLICATION_JSON_UTF_8})
public class UserService implements IUserSerivce{

    @GET
    @Path("getUser/{userCode}")
    @Cacheable(value = "demoCache" , key = "'user' + #userCode")
    public User getUser(@PathParam("userCode") Integer userCode){
        System.out.println("from db");
        return new User(userCode , "程欣伟");
    }


}
