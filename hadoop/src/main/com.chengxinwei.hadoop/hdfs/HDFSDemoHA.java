package com.chengxinwei.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by xinwei.cheng on 2015/10/16.
 */
public class HDFSDemoHA {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Configuration configuration = new Configuration();
        configuration.set("dfs.nameservices" , "ns1");
        configuration.set("dfs.ha.namenodes.ns1" , "nn1,nn2");
        configuration.set("dfs.namenode.rpc-address.ns1.nn1" , "master:9000");
        configuration.set("dfs.namenode.rpc-address.ns1.nn2" , "slave2:9000");
        configuration.set("dfs.client.failover.proxy.provider.ns1" , "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://ns1") , configuration);

        InputStream in = fileSystem.open(new Path("/test"));
        OutputStream out = new FileOutputStream("d://test.txt");
        IOUtils.copyBytes(in , out , 4096 , true);

    }

}
