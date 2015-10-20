package com.chengxinwei.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by xinwei.cheng on 2015/10/6.
 */
public class RPCClient {

    public static void main(String[] args) throws IOException {
        System.out.println(RPC.getProxy(BizAble.class, 100, new InetSocketAddress("192.168.0.104" , 28080) , new Configuration()).sayHi("spark"));

    }

}
