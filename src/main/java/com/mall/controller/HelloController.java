package com.mall.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HelloController {
    @RequestMapping("HelloJava")
    public String helloJava(){
        return "HelloJava";
    }
}
