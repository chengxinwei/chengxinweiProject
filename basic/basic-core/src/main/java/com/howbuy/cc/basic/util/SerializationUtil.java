package com.howbuy.cc.basic.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * 序列化的工具类
 * Created by xinwei.cheng on 2015/5/29.
 */
@SuppressWarnings("unused")
public class SerializationUtil {

    private final Logger logger = Logger.getLogger(this.getClass());

    public static byte[] serialize(Object object) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        }finally {
            if(oos != null){
                oos.close();
            }
            if(baos != null){
                baos.close();
            }
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }finally {
            if(bais != null){
                bais.close();
            }
        }
    }

}