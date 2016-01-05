package com.howbuy.cc.basic.kafka.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * Created by xinwei.cheng on 2015/10/21.
 */
public class ProducerTest {

    public static void main(String[] args) {
        Random rnd = new Random();

        Properties props = new Properties();
        props.put("metadata.broker.list", "192.168.221.21:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.howbuy.cc.basic.kafka.producer.SimplePartitioner");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<>(config);

        long runtime = new Date().getTime();
        KeyedMessage<String, String> data = new KeyedMessage<>("my-replicated-topic", "192.168.1.1", "test");

        producer.send(data);
        producer.close();
    }
}
