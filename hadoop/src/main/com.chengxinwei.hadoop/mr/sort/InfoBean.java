package com.chengxinwei.hadoop.mr.sort;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by xinwei.cheng on 2015/10/9.
 */
public class InfoBean implements WritableComparable<InfoBean> {

    private String account;
    private double income;
    private double expenses;
    private double surplus;

    public InfoBean() {
    }

    public InfoBean(String account, double income, double expenses) {
        this.account = account;
        this.income = income;
        this.expenses = expenses;
        this.surplus = income - expenses;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getSurplus() {
        return surplus;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }

    @Override
    public String toString() {
        return income +
                "\t" + expenses +
                "\t" + surplus;
    }

    @Override
    public int compareTo(InfoBean o) {
        if(this.income == o.income){
            return this.expenses > o.expenses ? -1 : 1;
        }else{
            return this.income > o.income ? 1 : -1;
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(account);
        out.writeDouble(income);
        out.writeDouble(expenses);
        out.writeDouble(surplus);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.account = in.readUTF();
        this.income = in.readDouble();
        this.expenses = in.readDouble();
        this.surplus = in.readDouble();
    }
}
