package com.howbuy.cc.basic.dubbo.execute;

import com.alibaba.dubbo.common.json.JSON;
import org.apache.commons.lang3.StringUtils;

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

        String[] methodClassAry = new String[0];
        String[] methodValueAry = new String[0];
        if(args.length > 4) {
            methodClassAry = args[4].split("[|]");

            int methodAryLength = args.length - 5;
            methodValueAry = new String[methodAryLength];
            for(int i = 0 ; i < methodAryLength ; i ++) {
                methodValueAry[i] = URLDecoder.decode(args[i + 5]);
            }
        }

        try {


            Object object = DubboService.execute(zookeeperHost, fullJarPath, interfaceName, methodName, methodClassAry, methodValueAry);
            System.out.println("-------------------------数据返回----------------------");
            System.out.println(JSON.json(object));
            System.out.println("-------------------------结束----------------------");
        } catch (Exception e) {
            StringWriter sw=new StringWriter();
            PrintWriter pw=new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println(sw.toString());
        }
    }
}
