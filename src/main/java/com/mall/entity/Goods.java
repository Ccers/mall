package com.mall.entity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class Goods {
    @GetMapping("/get")
    public String get(){
        return "good";
    }
}
