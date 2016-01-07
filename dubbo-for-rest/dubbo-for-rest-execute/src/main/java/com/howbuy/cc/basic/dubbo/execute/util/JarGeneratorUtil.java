package com.howbuy.cc.basic.dubbo.execute.util;


import com.howbuy.cc.basic.config.Configuration;
import com.howbuy.cc.basic.dubbo.execute.model.Pom;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 负责获取到 pom 的字符串，下载jar包
 * Created by xinwei.cheng on 2015/7/10.
 */
public class JarGeneratorUtil {

    final static  Logger logger = Logger.getLogger(JarGeneratorUtil.class);

//    String[] mavenUrl = new String[]{
//            "http://192.168.220.220:8081/nexus/service/local/repositories/com.howbuy.cc/content/${groupId}/${artifactId}/${version}/${jarname}" ,
//            "http://192.168.220.220:8081/nexus/service/local/repositories/com.howbuy.tx.snapshots/content/${groupId}/${artifactId}/1.0.0-SNAPSHOT/howbuy-txio-1.0.0-20150920.211843-367.jar"};

    /**
     * 根据 pom xml 字符串创建pom对象
     * @param pomStr pom xml 字符串
     * @return pom 对象
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static Pom getPomByStr(String pomStr) throws IOException, SAXException, ParserConfigurationException {
        //创建一个新的字符串
        StringReader read = new StringReader(pomStr);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();

        Pom pom = new Pom();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //得到根元素所有子元素的集合
            List childrenList = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Element et;
            for (int i = 0; i < childrenList.size(); i++) {
                et = (Element) childrenList.get(i);//循环依次得到子元素
                if (et.getName().equals("groupId")) {
                    pom.setGroupId(et.getText().trim());
                } else if (et.getName().equals("artifactId")) {
                    pom.setArtifactId(et.getText().trim());
                } else if (et.getName().equals("version")) {
                    pom.setVersion(et.getText().trim());
                }
            }
            String jarName = pom.getArtifactId() + "-" + pom.getVersion() + ".jar";
            pom.setJarName(jarName);
            pom.setFullJarPath(Configuration.get("jarFilePath")+jarName);
        } catch (JDOMException e) {
            logger.error(e.getMessage() , e);
        } catch (IOException e) {
            logger.error(e.getMessage() , e);
        }
        if (pom.getArtifactId() == null || pom.getGroupId() == null || pom.getVersion() == null) {
            return pom;
        }
        return pom;
    }


    /**
     * 根据pom download jar包
     * @param pom pom对象
     */
    public static void getJarByPom(Pom pom) {
        if(pom == null){
            throw new RuntimeException("pom is null");
        }
        String url = Configuration.get("maven.repositories.url");

        url = url.replace("${groupId}" , StringUtils.join(pom.getGroupId().split("\\.") , "/"));
        url = url.replace("${artifactId}" , pom.getArtifactId());
        url = url.replace("${version}" , pom.getVersion());
        url = url.replace("${jarname}" , pom.getJarName());
        downloadFile(url , pom.getFullJarPath());
    }

    //http://192.168.220.220:8081/nexus/service/local/repositories/com.howbuy.cc/content/com/howbuy/test/howbuy-cxcp-txio/1.0.0/howbuy-cxcp-txio-1.0.0.jar
    //http://192.168.220.220:8081/nexus/service/local/repositories/com.howbuy.cc/content/com/howbuy/test/howbuy-cxcp-txio/1.0.0/howbuy-cxcp-txio-1.0.0.jar

    /**
     * 下载远程文件并保存到本地
     * @param remoteFilePath 远程文件路径
     * @param localFilePath  本地文件路径
     */
    public static void downloadFile(String remoteFilePath, String localFilePath) {
        URL urlfile;
        HttpURLConnection httpUrl;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        getPomByStr("<dependency>\n" +
                "  <groupId>com.howbuy.cc.basic</groupId>\n" +
                "  <artifactId>basic-core</artifactId>\n" +
                "  <version>1.0.0</version>\n" +
                "</dependency>");
    }

}
