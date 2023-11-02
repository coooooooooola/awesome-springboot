package com.zeburan.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.zeburan.springboot.model.SessionUserInfo;
import org.apache.ibatis.annotations.Param;


public interface LoginDao {
    /**
     * 根据用户名和密码查询对应的用户
     */
    JSONObject checkUser(@Param("username") String username, @Param("password") String password);

    SessionUserInfo getUserInfo(String username);

}
