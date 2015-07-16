/**
 * 
 */
package com.howbuy.cc.basic.mybatis.datasourceRoute;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据路由
 */
@SuppressWarnings("unused")
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceSwitch.getDataSouce();
	}

}
