package com.howbuy.cc.basic.mvc;

import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.dubbo.execute.DubboService;
import com.howbuy.cc.basic.dubbo.execute.JarGenerator;
import com.howbuy.cc.basic.dubbo.execute.MethodParamName;
import com.howbuy.cc.basic.dubbo.execute.model.InterfaceInfo;
import com.howbuy.cc.basic.dubbo.execute.model.Pom;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
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
    public Object dubboExcute(String interfaceName,
                              @RequestParam(value = "valueAry[]" , required = false) String[] valueAry ,
                              @RequestParam(value = "methodParamsClassAry[]" , required = false) String[] methodParamsClassAry,
                              String methodName,
                              String fullJarPath
                              ) throws IOException {
        Map<String,Object> result = new HashMap<>();

        try {
            methodParamsClassAry = methodParamsClassAry == null ? new String[0] : methodParamsClassAry ;
            valueAry = valueAry == null ? new String[0] : valueAry;

            String paramsStr = StringUtils.join(valueAry , " ").replace("\"", "\\\"");
            String executePath = Configuration.get("executePath");
            String cmd = "java -jar -Dfile.encoding=UTF-8 " + executePath + "dubbo-for-rest-execute.jar" +
                    " " + fullJarPath +
                    " " + interfaceName +
                    " " + Configuration.get("zookeeper.ip_port") +
                    " " + methodName +
                    " " + StringUtils.join(methodParamsClassAry , "|") +
                    " " + paramsStr;
            logger.info("执行命令:" + cmd);
            Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in , "utf-8"));

            String lineStr;
            List<String> resultList = new ArrayList<>();
            result.put("status" , "ok");

            while ((lineStr = inBr.readLine()) != null) {
                //获得命令执行后在控制台的输出信息
                logger.info(lineStr);
                resultList.add(lineStr);
            }
            //检查命令是否执行失败。
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1) {
                    result.put("status" , "fail");
                    resultList.add("命令执行失败!");
                }
            }
            inBr.close();
            in.close();

            result.put("message" , resultList);
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

            List<Class> classList = DubboService.generator(pom , false);
            for (Class clazz : classList) {
                if (!clazz.isInterface()) {
                    continue;
                }

                Method[] methodAry = clazz.getMethods();
                for (int i = 0 ; i < methodAry.length ; i ++) {
                    Method method = methodAry[i];
                    InterfaceInfo interfaceInfo = new InterfaceInfo();
                    interfaceInfo.setClazz(clazz);
                    interfaceInfo.setMethodParamsInfoAry(MethodParamName.getMethodParamsInfo(clazz, method));
                    interfaceInfo.setMethod(method);
                    interfaceInfo.setFullJarPath(pom.getFullJarPath());
                    interfaceInfoList.add(interfaceInfo);
                }
            }
            result.put("status", "ok");
            result.put("artifactId", pom.getArtifactId());
            result.put("groupId", pom.getGroupId());
            result.put("resultList", interfaceInfoList);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
        result.put("status", "fail");
        return result;
    }
}
