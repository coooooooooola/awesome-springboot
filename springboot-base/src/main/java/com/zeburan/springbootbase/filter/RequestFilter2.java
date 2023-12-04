package com.zeburan.springbootbase.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * Create by swtywang on 11/4/23 4:28 PM
 */
@Component
@Order(2)
@Slf4j
public class RequestFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Filter.... RequestFilter2 before");
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("Filter.... RequestFilter2 after");
    }
}
