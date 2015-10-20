package com.chengxinwei.hadoop.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/8.
 */
public class WordCount {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration());
        job.setJarByClass(WordCount.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setMapperClass(WcMapper.class);
        FileInputFormat.setInputPaths(job , new Path("/words_input.txt"));

        job.setReducerClass(WcReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        FileOutputFormat.setOutputPath(job, new Path("/myWc"));

        job.setCombinerClass(WcReducer.class);

        job.waitForCompletion(true);

    }
}
