package com.zeburan.springbootbase.dao;


import com.zeburan.springbootbase.config.datasource.TargetDataSource;
import com.zeburan.springbootbase.model.SessionUserInfo;


public interface ThirdPartyDao {
    @TargetDataSource(name="xxljobSource")
    SessionUserInfo getUserInfo(String username);

}
