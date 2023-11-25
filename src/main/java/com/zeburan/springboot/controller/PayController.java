package com.zeburan.springboot.controller;


import com.zeburan.springboot.config.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {

    @RequiresPermissions("user:list")
    @GetMapping("/pay")
    public String pay() {
        System.out.println("aaa");
        System.out.println("aaa");
        System.out.println("aaa");
        return "ttt";
    }

}
