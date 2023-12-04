package com.zeburan.springbootbase.service;

import com.alibaba.fastjson.JSONObject;
import com.zeburan.springbootbase.dao.LoginDao;
import com.zeburan.springbootbase.exception.CommonJsonException;
import com.zeburan.springbootbase.model.SessionUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.UUID;


@Service
@Slf4j
public class TokenService {

    @Autowired
    Cache<String, SessionUserInfo> cacheMap;

    @Autowired
    LoginDao loginDao;


    /**
     * 用户登录验证通过后(sso/帐密),生成token,记录用户已登录的状态
     */
    public String generateToken(String username) {
        MDC.put("username", username);
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        //设置用户信息缓存
        setCache(token, username);
        return token;
    }

    public SessionUserInfo getUserInfo() {
        String token = MDC.get("token");
        return getUserInfoFromCache(token);
    }

    /**
     * 根据token查询用户信息
     * 如果token无效,会抛未登录的异常
     */
    private SessionUserInfo getUserInfoFromCache(String token)  {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1001);
        jsonObject.put("msg", "login error");
        if (token == null || token.isEmpty()) {
            throw new CommonJsonException(jsonObject);
        }
        log.debug("根据token从缓存中查询用户信息,{}", token);
        SessionUserInfo info = cacheMap.getIfPresent(token);
        if (info == null) {
            log.info("没拿到缓存 SessionUserInfo = null");
            throw new CommonJsonException(jsonObject);
        }
        return info;
    }

    private void setCache(String token, String username) {
        SessionUserInfo info = getUserInfoByUsername(username);
        log.info("设置用户信息缓存:token={} , username={}, info={}", token, username, info);
        cacheMap.put(token, info);
    }

    /**
     * 退出登录时,将token置为无效
     */
    public void invalidateToken() {
        String token = MDC.get("token");
        if (token == null || token.isEmpty()) {
            cacheMap.invalidate(token);
        }
        log.debug("退出登录,清除缓存:token={}", token);
    }

    private SessionUserInfo getUserInfoByUsername(String username) {
        SessionUserInfo userInfo = loginDao.getUserInfo(username);
        return userInfo;
    }

}
