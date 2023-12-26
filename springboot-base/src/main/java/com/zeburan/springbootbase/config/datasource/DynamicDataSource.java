package com.zeburan.springbootbase.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * 动态数据源，每次执行数据库操作，会调用AbstractRoutingDataSource.Connection
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> DATA_SOURCE_KEY = new ThreadLocal<>();

    static void changeDataSource(String dataSourceKey) {
        DATA_SOURCE_KEY.set(dataSourceKey);
    }

    static void clearDataSource() {
        DATA_SOURCE_KEY.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}