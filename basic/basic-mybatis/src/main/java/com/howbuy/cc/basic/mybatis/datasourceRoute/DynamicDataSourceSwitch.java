/**
 * 
 */
package com.howbuy.cc.basic.mybatis.datasourceRoute;

/**
 * 数据源切换
 */
public class DynamicDataSourceSwitch {


	public static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static void setDataSource(String name) {
        holder.set(name);
    }

    public static String getDataSouce() {
        return holder.get();
    }
}
