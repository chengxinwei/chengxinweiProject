package com.howbuy.cc.basic.dubbo.generator;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;

/**
 * 负责生成dubbo 的 xml
 * Created by xinwei.cheng on 2015/7/9.
 */
public class DubboXmlGenerator {

    public static String getFilePath(String interfaceClass){
        return "dubbo/dubbo-" + interfaceClass + ".xml";
    }

    public static String getFilePathWithBase(String interfaceClass){
        String basePath = DubboXmlGenerator.class.getClassLoader().getResource(".").getPath();
        return basePath + "dubbo/dubbo-" + interfaceClass + ".xml";
    }

    /**
     * 根据接口类生成dubbo的配置文件
     * @param interfaceClass
     * @throws IOException
     */
    public static void generator(String interfaceClass) throws IOException {
        int byteread;

        try {
            Class.forName(interfaceClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String basePath = DubboXmlGenerator.class.getClassLoader().getResource(".").getPath();
        File templateFile = new File(basePath + "/dubbo/dubbo-t.template");
        String newFileName = getFilePathWithBase(interfaceClass);
        if(templateFile.exists()){
            InputStream inStream = new FileInputStream(templateFile); //读入原文件
            FileOutputStream fs = new FileOutputStream(newFileName);

            String template = "";
            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                template = template + new String(ArrayUtils.subarray(buffer , 0 , byteread));
            }

            template = template.replace("${interfaceClass}" , interfaceClass);

            fs.write(template.getBytes(), 0, template.getBytes().length);
            inStream.close();
        }
    }

}
