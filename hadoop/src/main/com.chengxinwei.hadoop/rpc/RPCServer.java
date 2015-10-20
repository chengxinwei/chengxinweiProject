package com.chengxinwei.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/6.
 */
public class RPCServer implements BizAble{


    public String sayHi(String name){
        return "HI~" + name;
    }


    public static void main(String[] args) throws IOException {
        RPC.Server server = new RPC.Builder(new Configuration()).
                setProtocol(BizAble.class).
                setInstance(new RPCServer()).
                setBindAddress("192.168.0.104").
                setPort(28080).
                build();
        server.start();
    }

}
