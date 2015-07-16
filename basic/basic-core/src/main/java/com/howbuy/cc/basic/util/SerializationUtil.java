package com.howbuy.cc.basic.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * ���л�������
 * Created by xinwei.cheng on 2015/5/29.
 */
@SuppressWarnings("unused")
public class SerializationUtil {

    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * ���л�
     * @param object ��Ҫ���л��Ķ���
     * @return ���л�֮����ֽ�����
     */
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

    /**
     * �����л�
     * @param bytes ���л�֮���byte����
     * @return ���ض���
     */
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