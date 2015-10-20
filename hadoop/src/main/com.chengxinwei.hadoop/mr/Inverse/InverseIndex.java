package com.chengxinwei.hadoop.mr.Inverse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/10.
 */
public class InverseIndex {


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration());
        job.setJarByClass(InverseIndex.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setMapperClass(IndexMapper.class);
        FileInputFormat.setInputPaths(job , new Path(args[0]));

        job.setCombinerClass(IndexCombiner.class);


        job.setReducerClass(IndexReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileOutputFormat.setOutputPath(job , new Path(args[1]));


        job.waitForCompletion(true);

    }


    public static class IndexMapper extends Mapper<LongWritable, Text , Text , Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] wordCount = value.toString().split(" ");
            String fileName = ((FileSplit)context.getInputSplit()).getPath().toString();
            for(String str : wordCount){
                context.write(new Text(str + " " + fileName) , new Text("1"));
            }
        }
    }


    public static class IndexCombiner extends Reducer<Text , Text , Text , Text>{

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            long sum = 0;
            for(Text i : values){
                sum+= Long.parseLong(i.toString());
            }
            String[] keyAry = key.toString().split(" ");
            context.write(new Text(keyAry[0]) , new Text(keyAry[1] + "->" + sum));
        }
    }


    public static class IndexReducer extends Reducer<Text , Text , Text , Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String result = "";
            for(Text str : values){
                result += str.toString() + "\t";
            }
            context.write(key , new Text(result));
        }
    }

}
