package com.zeburan.springboot.controller;

import cn.hutool.json.JSONObject;
import com.zeburan.springboot.config.annotation.RequiresPermissions;
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

    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public String listUser(HttpServletRequest request) {
        return "ttt";
    }
}
