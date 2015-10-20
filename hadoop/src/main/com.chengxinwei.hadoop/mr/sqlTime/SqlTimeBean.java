package com.chengxinwei.hadoop.mr.sqlTime;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/8.
 */
public class SqlTimeBean implements Writable {

    private long avg;
    private long max;
    private long min;


    public SqlTimeBean() {
    }

    public SqlTimeBean(long avg, long max, long min) {
        this.avg = avg;
        this.max = max;
        this.min = min;
    }

    //Serializable
    public void write(DataOutput out) throws IOException {
        out.writeLong(avg);
        out.writeLong(max);
        out.writeLong(min);
    }

    //deSerializable
    public void readFields(DataInput in) throws IOException {
        this.avg = in.readLong();
        this.max = in.readLong();
        this.min = in.readLong();

    }

    @Override
    public String toString() {
        return this.avg + "\t" + this.max + "\t" + this.min;
    }
}
