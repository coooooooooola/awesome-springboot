package com.zeburan.springboot.controller;

import cn.hutool.json.JSONObject;
import com.zeburan.springboot.config.annotation.RequiresPermissions;
import com.zeburan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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


//    @RequiresPermissions("user:list")
    @GetMapping("/aaa")
    public String listUsera() {
        userService.userlist();
        return "a";
    }
}
