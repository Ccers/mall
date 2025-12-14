package com.mall.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String mobile; // 注册时可选填手机号
}