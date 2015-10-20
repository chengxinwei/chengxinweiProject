package com.chengxinwei.hadoop.mr.sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/9.
 */
public class SumStep {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(SumStep.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(InfoBean.class);
        job.setMapperClass(SumMapper.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(InfoBean.class);
        job.setReducerClass(SumReducer.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }


    public static class SumMapper extends Mapper<LongWritable , Text, Text , InfoBean >{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\t");
            context.write(new Text(fields[0]) , new InfoBean(fields[0] , Double.parseDouble(fields[1]) , Double.parseDouble(fields[2])));
        }
    }

    public static class SumReducer extends Reducer<Text , InfoBean , Text , InfoBean>{
        @Override
        protected void reduce(Text key, Iterable<InfoBean> values, Context context) throws IOException, InterruptedException {
            double inSum = 0;
            double outSum = 0;
            for(InfoBean infoBean : values){
                inSum += infoBean.getIncome();
                outSum += infoBean.getExpenses();
            }

            context.write(key , new InfoBean(key.toString() , inSum , outSum));
        }
    }

}
