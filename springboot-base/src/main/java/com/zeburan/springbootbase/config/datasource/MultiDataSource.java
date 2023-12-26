package com.zeburan.springbootbase.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author：zeburan
 * @Date：2023/12/26 9:14
 */
@Configuration
public class MultiDataSource {

    public static final String MASTER_DATA_SOURCE = "masterSource";
    public static final String XXL_JOB_SOURCE = "xxljobSource";

    @Bean(name = MultiDataSource.MASTER_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = MultiDataSource.XXL_JOB_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.xxl")
    public DataSource xxlJobSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put(MASTER_DATA_SOURCE, masterDataSource());
        dataSourceMap.put(XXL_JOB_SOURCE, xxlJobSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }
}
