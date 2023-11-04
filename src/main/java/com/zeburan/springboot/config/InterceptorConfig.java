package com.zeburan.springboot.config;

import com.zeburan.springboot.interceptor.InterceptorDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create by swtywang on 11/4/23 6:26 PM
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorDemo interceptorDemo;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // ** 表示所有拦截路径
        registry.addInterceptor(interceptorDemo).addPathPatterns("/**");
        // 或下面这种写法  【若编写自定义拦截器类没有加@Component注解】
        //registry.addInterceptor(new InterceptorDemo()).addPathPatterns("/**");
    }
}
