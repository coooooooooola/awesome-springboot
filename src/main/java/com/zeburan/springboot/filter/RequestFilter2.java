package com.zeburan.springboot.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * Create by swtywang on 11/4/23 4:28 PM
 */
@Component
@Order(2)
public class RequestFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter.... RequestFilter2 before");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter.... RequestFilter2 after");
    }
}
