package com.howbuy.cc.basic.dubbo.execute.service.hsb;

import com.howbuy.hsb.hsbclient.HSB;
import com.howbuy.hsb.hsbclient.HSBClient;
import com.howbuy.hsb.txio.BaseTxRequest;
import com.howbuy.hsb.txio.BaseTxResponse;
import org.nutz.json.Json;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.lang.reflect.Field;

/**
 * Created by xinwei.cheng on 2015/11/30.
 */
public class HsbExecute {


    public static BaseTxResponse execute(String ip, String port, String communPort, String recognizers, String clazzStr, String valueJson) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
//        String ip = "192.168.221.28";
//        String port = "13242";
//        String communPort = "13244";
//        String recognizers = "20";
//        String clazzStr = "com.howbuy.txio.fds3.account.query.querycusttxinfo.QueryCustTxInfoRequest";

        Class<? extends BaseTxRequest> clazz = (Class<? extends BaseTxRequest>) Class.forName(clazzStr);

        HsbConfig.map.put("ip" , ip);
        HsbConfig.map.put("port" , port);
        HsbConfig.map.put("communPort" , communPort);
        HsbConfig.map.put("recognizers" , recognizers);

        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(new String[] {"classpath:spring.xml"});
        context.start();

        HSBClient hsbClient = HSBClient.getInstance();
        Field field = hsbClient.getClass().getDeclaredField("appContext");
        field.setAccessible(true);
        field.set(hsbClient , context);

        HSB hsb = HSB.getInstance();
        field = hsb.getClass().getDeclaredField("appContext");
        field.setAccessible(true);
        field.set(hsb , context);

//        String jsonStr = "{\"idNo\" : \"430701198801010075\" , \"idType\" : \"0\" , \"disCode\" : \"HB000A001\"}";
        BaseTxRequest request = Json.fromJson(clazz , valueJson);
        return HSB.getInstance().callService(request, null);
    }

}
