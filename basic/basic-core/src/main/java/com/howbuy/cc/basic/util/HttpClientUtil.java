package com.howbuy.cc.basic.util;

import com.howbuy.cc.basic.logger.CCLogger;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/8/14.
 */
public class HttpClientUtil {

    static CCLogger logger = CCLogger.getLogger(HttpClientUtil.class);

    /**
     * 执行一个HTTP GET请求
     * @param url 请求的URL地址
     * @return 返回请求响应的
     */
    /**
     * 发送 get请求
     */
    public static Map<Integer,String> get(String url , Map<String,String> params) {
        Map<Integer,String> result = new HashMap<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(getUrl(url , params));
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                int httpCode = response.getStatusLine().getStatusCode();
                if (entity != null) {
                    result.put(httpCode , EntityUtils.toString(entity));
                }else{
                    result.put(httpCode , null);
                }
                return result;
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }


    private static String getUrl(String url , Map<String,String> params){
        if(params == null || params.isEmpty()){
            return url;
        }
        StringBuilder paramsSb = new StringBuilder();
        for(Map.Entry entry : params.entrySet()){
            paramsSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return url.endsWith("?") ? url + paramsSb.toString() : url + "?" + paramsSb.toString();
    }


    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static Map<Integer , String> post(String url , Map<String,String> params) {
        Map<Integer,String> result = new HashMap<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                int httpCode = response.getStatusLine().getStatusCode();
                if (entity != null) {
                    result.put(httpCode , EntityUtils.toString(entity, "UTF-8"));
                }else{
                    result.put(httpCode , null);
                }
                return result;
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }
}
