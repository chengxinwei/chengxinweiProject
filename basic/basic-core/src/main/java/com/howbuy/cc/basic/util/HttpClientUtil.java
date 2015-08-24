//package com.howbuy.cc.basic.util;
//
//import com.howbuy.cc.basic.logger.CCLogger;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpMethod;
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.params.HttpMethodParams;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URLEncoder;
//import java.util.*;
//
///**
// * Created by xinwei.cheng on 2015/8/14.
// */
//public class HttpClientUtil {
//
//    static CCLogger logger = CCLogger.getLogger(HttpClientUtil.class);
//
//    /**
//     * 执行一个HTTP GET请求，返回请求响应的HTML
//     *
//     * @param url         请求的URL地址
//     * @return 返回请求响应的HTML
//     */
//    public static String doGet(String url, Map<String,String> queryMap) {
//        StringBuffer response = new StringBuffer();
//        HttpClient client = new HttpClient();
//        HttpMethod method = new GetMethod(url);
//        try {
//            if (queryMap != null && queryMap.size() > 0) {
//                List<NameValuePair> list = new ArrayList<>();
//                for(Map.Entry<String,String> entry : queryMap.entrySet()) {
//                    list.add(new NameValuePair(entry.getKey() , URLEncoder.encode(entry.getValue())));
//                }
//                method.setQueryString(list.toArray(new NameValuePair[list.size()]));
//            }
//            client.executeMethod(method);
//            if (method.getStatusCode() == HttpStatus.SC_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "utf-8"));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//            }
//        } catch (IOException e) {
//            logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
//        } finally {
//            method.releaseConnection();
//        }
//        return response.toString();
//    }
//
//    /**
//     * 执行一个HTTP POST请求，返回请求响应的HTML
//     *
//     * @param url     请求的URL地址
//     * @param params  请求的查询参数,可以为null
//     * @return 返回请求响应的HTML
//     */
//    public static String doPost(String url, Map<String, String> params) {
//        StringBuffer response = new StringBuffer();
//        HttpClient client = new HttpClient();
//        HttpMethod method = new PostMethod(url);
//        //设置Http Post数据
//        if (params != null) {
//            HttpMethodParams p = new HttpMethodParams();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                p.setParameter(entry.getKey(), entry.getValue());
//            }
//            method.setParams(p);
//        }
//        try {
//            client.executeMethod(method);
//            if (method.getStatusCode() == HttpStatus.SC_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "utf-8"));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//            }
//        } catch (IOException e) {
//            logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
//        } finally {
//            method.releaseConnection();
//        }
//        return response.toString();
//    }
//}
