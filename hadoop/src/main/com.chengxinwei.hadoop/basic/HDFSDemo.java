package com.chengxinwei.hadoop.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by xinwei.cheng on 2015/9/30.
 */
public class HDFSDemo {

    FileSystem fs;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        fs = FileSystem.get(new URI("hdfs://192.168.187.129:9000") , new Configuration() , "chengxinwei");
    }


    @Test
    public void testUpload(){
        try {
            InputStream in = new FileInputStream("D:\\workspace\\chengxinweiProject\\hadoop\\words_result.txt");
            OutputStream out = fs.create(new Path("/words_result2.txt"));
            IOUtils.copyBytes(in , out , 4096 , true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDownload() throws IOException {
        fs.copyToLocalFile(false , new Path("/words_input.txt") , new Path("D:\\workspace\\chengxinweiProject\\hadoop\\a.txt") , true);
    }


    @Test
    public void testDel() throws IOException {
        fs.delete(new Path("/words_result2.txt") , false);
    }

    @Test
    public void testMkdir() throws IOException {
        fs.mkdirs(new Path("/testMkdir"));
    }

    @Test
    public void testDelDir() throws IOException {
        fs.delete(new Path("/testMkdir") , true);
    }


    public static void main(String[] args) throws URISyntaxException, IOException {
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.187.129:9000") , new Configuration());
        InputStream in = fs.open(new Path("/words_input.txt"));
        OutputStream out = new FileOutputStream("D:\\workspace\\chengxinweiProject\\hadoop\\words_result.txt");
        IOUtils.copyBytes(in , out , 4096 , true);
    }


}
