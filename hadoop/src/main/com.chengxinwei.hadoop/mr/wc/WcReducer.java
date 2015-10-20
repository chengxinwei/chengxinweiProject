package com.chengxinwei.hadoop.mr.wc;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/8.
 */
public class WcReducer extends Reducer<Text, LongWritable , Text , LongWritable> {

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long sum = 0 ;
        for(LongWritable i : values){
            sum += i.get();
        }
        context.write(key , new LongWritable(sum));
    }
}
