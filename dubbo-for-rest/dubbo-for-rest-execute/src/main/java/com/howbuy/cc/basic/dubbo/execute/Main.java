package com.howbuy.cc.basic.dubbo.execute;

import com.howbuy.cc.basic.dubbo.execute.model.ExecuteInfo;
import com.howbuy.cc.basic.dubbo.execute.service.common.ExecuteService;
import com.howbuy.cc.basic.dubbo.execute.service.dubbo.DubboExecute;
import com.howbuy.cc.basic.dubbo.execute.service.hsb.HsbExecute;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;

/**
 * Hello world!
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("获取参数" + StringUtils.join(args , "  |  "));
        if(args.length < 4){
            System.out.println("参数错误");
            return;
        }
        String fullJarPath = args[0];
        String interfaceName = args[1];
        String zookeeperHost = args[2];
        String methodName = args[3];
        String ip = args[4];
        String port = args[5];
        String communPort = args[6];
        String recognizers = args[7];
        boolean isDubbo = Boolean.valueOf(args[8]);
        String[] methodClassAry = args[9].equals("null") ? new String[]{} : args[9].split("[|]");

        int offset = 10;
        int methodAryLength = args.length - offset;
        String[] methodValueAry = new String[methodAryLength];
        for(int i = 0 ; i < methodAryLength ; i ++) {
            methodValueAry[i] = URLDecoder.decode(args[i + offset]);
        }

        try {
            ExecuteInfo executeInfo = ExecuteService.prepareExecute(fullJarPath, interfaceName, methodName, methodClassAry, isDubbo , methodValueAry);
            Object object;
            if(isDubbo) {
                object = DubboExecute.execute(zookeeperHost, executeInfo.getClazz(), executeInfo.getMethod(), executeInfo.getArgs());
            }else{
                object = HsbExecute.execute(ip , port , communPort , recognizers , interfaceName , methodValueAry[0]);
            }
            System.out.println("-------------------------数据返回----------------------");
            System.out.println(Json.toJson(object));
            System.out.println("-------------------------结束----------------------");
        } catch (Exception e) {
            StringWriter sw=new StringWriter();
            PrintWriter pw=new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println(sw.toString());
        }
    }


}
