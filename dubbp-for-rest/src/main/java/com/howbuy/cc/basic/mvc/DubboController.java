package com.howbuy.cc.basic.mvc;

import com.howbuy.cc.basic.dubbo.generator.DubboService;
import com.howbuy.cc.basic.dubbo.generator.MethodParamName;
import com.howbuy.cc.basic.dubbo.generator.model.InterfaceInfo;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/7/13.
 */
@Controller
@RequestMapping("/dubbo")
public class DubboController {

    Logger logger = Logger.getLogger(this.getClass());


    /**
     * dubbo主页面
     * @return
     */
    public String index(String pomStr){

        return "index";
    }


    @ResponseBody
    @RequestMapping("/excute")
    public Object dubboExcute(String interfaceName, String method, @RequestParam("methodParamsClassAry[]") Class[] methodParamsClass, @RequestParam("valueAry[]") String[] valueAry) throws IOException {
        Map<String,Object> result = new HashMap<>();

        try {
            Object obj = DubboService.excute(interfaceName , method , methodParamsClass , valueAry);
            result.put("status" , "ok");
            result.put("message" , obj);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
            result.put("status" , "fail");


            StringWriter sw=new StringWriter();
            PrintWriter pw=new PrintWriter(sw);
            e.printStackTrace(pw);
            result.put("message" , sw.toString());
            return  result;
        }
    }

    @ResponseBody
    @RequestMapping("/pom")
    public Map<String, Object> pom(String pomStr) throws IOException {
        List<InterfaceInfo> interfaceInfoList = new ArrayList<>();
        try {
            List<Class> classList = DubboService.generator(pomStr);
            for(Class clazz : classList ){
                if(!clazz.isInterface()){
                    continue;
                }

                for(Method method : clazz.getMethods()){
                    InterfaceInfo  interfaceInfo = new InterfaceInfo();
                    interfaceInfo.setClazz(clazz);
                    interfaceInfo.setMethodParamsInfoAry(MethodParamName.getMethodParamsName(clazz, method));
                    interfaceInfo.setMethod(method);
                    interfaceInfoList.add(interfaceInfo);
                }

            }
        }catch(Exception e){
            logger.error(e.getMessage() , e);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("resultList" , interfaceInfoList);
        return result;
    }

}
