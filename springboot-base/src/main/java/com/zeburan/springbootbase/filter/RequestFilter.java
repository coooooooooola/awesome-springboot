package com.zeburan.springbootbase.filter;

import com.zeburan.springbootbase.exception.CommonJsonException;
import com.zeburan.springbootbase.model.SessionUserInfo;
import com.zeburan.springbootbase.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author heeexy
 */
@Component
@Slf4j
@Order(1)
public class RequestFilter extends OncePerRequestFilter implements Filter {

    @Autowired
    TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
//            // 如果druid的请求，则不处理
//            if(request.getRequestURI().contains("/druid")){
//                filterChain.doFilter(request, response);
//                return;
//            }
//            //每个请求记录一个traceId,可以根据traceId搜索出本次请求的全部相关日志
//            MDC.put("traceId", UUID.randomUUID().toString().replace("-", "").substring(0, 12));
//            setUsername(request);
//            //使request中的body可以重复读取 https://juejin.im/post/6858037733776949262#heading-4
//            request = new ContentCachingRequestWrapper(request);
            log.info("Filter.... RequestFilter1 before");
            filterChain.doFilter(request, response);
            log.info("Filter.... RequestFilter1 after");
        } catch (Exception e) {
//            throw e;
            log.error("doFilterInternal方法出现异常：", e);
        } finally {
            //清理ThreadLocal
            MDC.clear();
        }
    }


    private void setUsername(HttpServletRequest request) {
        //通过token解析出username
        String token = request.getHeader("token");
//        if (!StringTools.isNullOrEmpty(token)) {
        MDC.put("token", token);
        try {
            SessionUserInfo info = tokenService.getUserInfo();
            if (info != null) {
                String username = info.getUsername();
                MDC.put("username", username);
            }
        } catch (CommonJsonException e) {
            log.info("无效的token:{}", token);
        }
//        }
    }
}
