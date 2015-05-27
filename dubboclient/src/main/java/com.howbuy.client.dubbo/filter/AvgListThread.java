package com.howbuy.client.dubbo.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by xinwei.cheng on 2015/5/26.
 */
public class AvgListThread extends Thread {


    public void run(){
        while(true) {

            long sum = 0;
            int total = 0;
            for (int i = 0; i < DubboFilter.avgList.size(); i++) {
                sum += DubboFilter.avgList.get(i);
                total += 1;
            }
            if(total != 0) {
                System.out.println(sum / total);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
