package com.chengxinwei.hadoop.mr.sort;

import javafx.scene.control.TextField;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
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
public class SortStep {



    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(SumStep.class);

        job.setMapOutputKeyClass(InfoBean.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setMapperClass(SortMapper.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(InfoBean.class);
        job.setReducerClass(SortReducer.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }


    public static class SortMapper extends Mapper<LongWritable , Text , InfoBean , NullWritable>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\t");
            context.write(new InfoBean(fields[0], Double.parseDouble(fields[1]), Double.parseDouble(fields[2])), NullWritable.get());
        }

    }

    public static class SortReducer extends Reducer<InfoBean , NullWritable , Text , InfoBean>{
        @Override
        protected void reduce(InfoBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(new Text(key.getAccount()) , key);
        }
    }



}
