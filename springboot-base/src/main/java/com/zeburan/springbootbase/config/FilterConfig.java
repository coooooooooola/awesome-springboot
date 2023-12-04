package com.zeburan.springbootbase.config;

import com.zeburan.springbootbase.filter.RequestFilter;
import com.zeburan.springbootbase.filter.RequestFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by swtywang on 11/4/23 4:51 PM
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registerMyFilter(){
        FilterRegistrationBean<RequestFilter> bean = new FilterRegistrationBean<>();
        bean.setOrder(1);
        bean.setFilter(new RequestFilter());
        // 匹配"/hello/"下面的所有url
        bean.addUrlPatterns("/user/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean registerMyAnotherFilter(){
        FilterRegistrationBean<RequestFilter2> bean = new FilterRegistrationBean<>();
        bean.setOrder(2);
        bean.setFilter(new RequestFilter2());
        // 匹配所有url
        bean.addUrlPatterns("/*");
        return bean;
    }
}
