package com.chengxinwei.hadoop.mr.data;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinwei.cheng on 2015/10/8.
 */
public class DataCount {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(DataCount.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DataBean.class);
        job.setMapperClass(DCMapper.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DataBean.class);
        job.setReducerClass(DCReducer.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setPartitionerClass(ProviderPartitioner.class);
        job.setNumReduceTasks(Integer.parseInt(args[2]));
        job.waitForCompletion(true);
    }


    public static class ProviderPartitioner extends Partitioner<Text, DataBean>{

        private static Map<String,Integer> providerMap = new HashMap<>();

        static {
            providerMap.put("135" , 1);
            providerMap.put("136" , 1);
            providerMap.put("137" , 1);
            providerMap.put("138" , 1);
            providerMap.put("139" , 1);
            providerMap.put("150" , 2);
            providerMap.put("159" , 2);
            providerMap.put("182" , 3);
            providerMap.put("183" , 3);
        }

        @Override
        public int getPartition(Text text, DataBean dataBean, int numPartitions) {
            String account = text.toString();
            String subAcc = account.substring(0 , 3);
            Integer code = providerMap.get(subAcc);
            return code == null ? 0 : code;
        }
    }


    public static class DCMapper extends Mapper<LongWritable , Text , Text, DataBean>{

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] ary = value.toString().split("\t");
            context.write(new Text(ary[1]) , new DataBean(ary[1] , Long.parseLong(ary[8]) ,  Long.parseLong(ary[9])));
        }
    }


    public static class DCReducer extends Reducer<Text , DataBean , Text , DataBean>{
        @Override
        protected void reduce(Text key, Iterable<DataBean> values, Context context) throws IOException, InterruptedException {
            long upSum = 0 ;
            long downSum = 0;
            for(DataBean dataBean : values){
                upSum += dataBean.getUpPayLoad();
                downSum += dataBean.getDownPayLoad();
            }

            DataBean dataBean = new DataBean("" , upSum , downSum);
            context.write(key , dataBean);
        }
    }
}
