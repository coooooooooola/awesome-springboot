package com.zeburan.springbootbase.controller;

import com.zeburan.springbootbase.config.annotation.RequiresPermissions;
import com.zeburan.springbootbase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Create by swtywang on 11/2/23 8:13 PM
 */


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public String listUser() {
        return "ttt";
    }
    public JSONObject test(HttpServletRequest request) {
        return new JSONObject();
    }


//    @RequiresPermissions("user:list")
    @GetMapping("/aaa")
    public String listUsera() {
        userService.userlist();
        return "a";
    }
}
