package com.mall.entity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class Comment {
    @GetMapping("/get")
    public String get(){
        return "comment";
    }
}
