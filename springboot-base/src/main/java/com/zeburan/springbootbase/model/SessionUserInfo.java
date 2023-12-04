package com.zeburan.springbootbase.model;

import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
public class SessionUserInfo {

    private int userId;

    private String username;

    private String nickname;
}
