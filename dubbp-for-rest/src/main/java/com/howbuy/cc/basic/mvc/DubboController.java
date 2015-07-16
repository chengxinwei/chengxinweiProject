package com.howbuy.cc.basic.mvc;

import com.howbuy.cc.basic.dubbo.generator.DubboService;
import com.howbuy.cc.basic.dubbo.generator.JarGenerator;
import com.howbuy.cc.basic.dubbo.generator.MethodParamName;
import com.howbuy.cc.basic.dubbo.generator.model.InterfaceInfo;
import com.howbuy.cc.basic.dubbo.generator.model.Pom;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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


    @ResponseBody
    @RequestMapping("/excute")
    public Object dubboExcute(String interfaceName, String method ,
                              @RequestParam("methodParamsClassAry[]") Class[] methodParamsClass,
                              @RequestParam("valueAry[]") String[] valueAry ,
                              String groupId ,
                              String artifactId) throws IOException {
        Map<String,Object> result = new HashMap<>();

        try {
            Object obj = DubboService.excute(artifactId, groupId  , interfaceName , method , methodParamsClass , valueAry);
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
        Map<String, Object> result = new HashMap<>();
        List<InterfaceInfo> interfaceInfoList = new ArrayList<>();
        try {
            Pom pom = JarGenerator.getPomByStr(pomStr);
            JarGenerator.getJarByPom(pom);

            List<Class> classList = DubboService.generator(pom);
            for (Class clazz : classList) {
                if (!clazz.isInterface()) {
                    continue;
                }

                for (Method method : clazz.getMethods()) {
                    InterfaceInfo interfaceInfo = new InterfaceInfo();
                    interfaceInfo.setClazz(clazz);
                    interfaceInfo.setMethodParamsInfoAry(MethodParamName.getMethodParamsInfo(clazz, method));
                    interfaceInfo.setMethod(method);
                    interfaceInfoList.add(interfaceInfo);
                }
                result.put("status", "ok");
                result.put("artifactId", pom.getArtifactId());
                result.put("groupId", pom.getGroupId());
                result.put("resultList", interfaceInfoList);
                return result;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
        result.put("status", "fail");
        return result;
    }
}
