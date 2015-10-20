package com.chengxinwei.hadoop.mr.wc;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/8.
 */
public class WcMapper extends Mapper<LongWritable, Text, Text , LongWritable> {


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        for(String word : words){
            context.write(new Text(word) , new LongWritable(1));
        }
    }
}
