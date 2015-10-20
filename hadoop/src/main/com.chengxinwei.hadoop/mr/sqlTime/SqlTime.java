package com.chengxinwei.hadoop.mr.sqlTime;

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
 * Created by xinwei.cheng on 2015/10/8.
 */
public class SqlTime {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(SqlTime.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setMapperClass(SqlTimeMapper.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(SqlTimeBean.class);
        job.setReducerClass(SqlTimeReducer.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }


    public static class SqlTimeMapper extends Mapper<LongWritable , Text , Text, LongWritable>{

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] ary = value.toString().split("[|]");
            if(ary.length != 8){
                System.out.println(value);
                return;
            }
            context.write(new Text(ary[5]) , new LongWritable(Integer.parseInt(ary[7])));
        }
    }

    public static class SqlTimeReducer extends Reducer<Text , LongWritable, Text , SqlTimeBean>{
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long index = 0;
            long sum = 0;
            long max = 0;
            long min = 0;
            for(LongWritable time : values){
                long timeL = time.get();
                sum += timeL;
                index++;
                if(timeL > max){
                    max = timeL;
                }
                if(timeL < min){
                    min = timeL;
                }
            }

            context.write(key , new SqlTimeBean(sum/index , max , min));
        }
    }
}
