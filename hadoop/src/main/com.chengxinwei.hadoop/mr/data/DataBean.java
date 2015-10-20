package com.chengxinwei.hadoop.mr.data;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/8.
 */
public class DataBean implements Writable {

    private String telNo;
    private long upPayLoad;
    private long downPayLoad;
    private long totalPayLoad;


    public DataBean() {
    }

    public DataBean(String telNo, long upPayLoad, long downPayLoad) {
        this.telNo = telNo;
        this.upPayLoad = upPayLoad;
        this.downPayLoad = downPayLoad;
        this.totalPayLoad = upPayLoad + downPayLoad;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public long getUpPayLoad() {
        return upPayLoad;
    }

    public void setUpPayLoad(long upPayLoad) {
        this.upPayLoad = upPayLoad;
    }

    public long getDownPayLoad() {
        return downPayLoad;
    }

    public void setDownPayLoad(long downPayLoad) {
        this.downPayLoad = downPayLoad;
    }

    public long getTotalPayLoad() {
        return totalPayLoad;
    }

    public void setTotalPayLoad(long totalPayLoad) {
        this.totalPayLoad = totalPayLoad;
    }

    //Serializable
    public void write(DataOutput out) throws IOException {
        out.writeUTF(telNo);
        out.writeLong(upPayLoad);
        out.writeLong(downPayLoad);
        out.writeLong(totalPayLoad);
    }

    //deSerializable
    public void readFields(DataInput in) throws IOException {
        this.telNo = in.readUTF();
        this.upPayLoad = in.readLong();
        this.downPayLoad = in.readLong();
        this.totalPayLoad = in.readLong();

    }

    @Override
    public String toString() {
        return this.upPayLoad + "\t" + this.downPayLoad + "\t" + this.totalPayLoad;
    }
}
