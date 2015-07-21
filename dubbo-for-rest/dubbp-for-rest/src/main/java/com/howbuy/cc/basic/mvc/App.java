package com.howbuy.cc.basic.mvc;

import com.alibaba.dubbo.common.json.JSON;
import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.dubbo.execute.JarGenerator;
import com.howbuy.cc.basic.dubbo.execute.model.Pom;
import javassist.ClassPath;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/7/17.
 */
public class App {


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        new ClassPathXmlApplicationContext("classpath:basic/spring/base-spring.xml");
        String pomStr = "<dependency>" +
                "<groupId>com.howbuy</groupId>" +
                " \t\t\t<artifactId>cim-web-client</artifactId>\n" +
                " \t\t\t<version>0.0.1</version>\n" +
                " \t\t</dependency>\n";

        pomStr = pomStr.replace("\t" , "").replace("\n" , "").replace(" " , "");
        Pom pom = JarGenerator.getPomByStr(pomStr);
        JarGenerator.getJarByPom(pom);

        System.out.println("----------------");
        Map<String,String> params = new HashMap<>();
        params.put("custNo" , "12312313");
        String paramsStr = JSON.json(params).replace("\"" , "\\\"");
        String cmd = "java -jar -Dfile.encoding=UTF-8 d:/workspace/chengxinweiProject/dubbo-for-rest/dubbo-for-rest-execute/target/dubbo-for-rest-execute.jar " +
                pom.getFullJarPath() + " com.howbuy.service.savingbank.SavingBankService " + Configuration.get("zookeeper.ip_port") + " 1 " + " " + paramsStr;
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                //获得命令执行后在控制台的输出信息
                System.out.println(lineStr);// 打印输出信息
            //检查命令是否执行失败。
            if (p.waitFor() != 0) {
                if (p.exitValue() == 1) {
                    System.err.println("命令执行失败!");
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
