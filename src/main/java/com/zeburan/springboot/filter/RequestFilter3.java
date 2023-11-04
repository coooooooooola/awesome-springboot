package com.zeburan.springboot.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Create by swtywang on 11/4/23 4:42 PM
 */
@WebFilter(urlPatterns = "/user/aaa")
public class RequestFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter.... RequestFilter3 before");
        // 要继续处理请求，必须添加 filterChain.doFilter()
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter.... RequestFilter3 after");
    }
}