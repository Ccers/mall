package com.mall.controller;

import com.mall.entity.User;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.PasswordUtil;
import com.mall.common.Result;
import com.mall.dto.UserLoginDTO;
import com.mall.dto.UserRegisterDTO;
import com.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
//消费者
import com.mall.entity.CustomerUser; // 引入新实体
import com.mall.mapper.CustomerUserMapper; // 引入新Mapper
import org.springframework.transaction.annotation.Transactional; // 引入事务注解
import java.util.HashMap;
import java.util.Map;
// 临时 DTO 类
@Data
class UserUpdateDTO {
    private String name;
    private int age;
}

@Slf4j // 启用日志功能
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomerUserMapper customerUserMapper; // 注入扩展表的Mapper

    /**
     * 1. 注册接口
     */
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class) //  开启事务：任何一步报错，全部回滚 前端检查输入的位数与格式
    public Result<String> register(@RequestBody UserRegisterDTO registerDTO) {
        log.info("收到注册请求: {}", registerDTO.getUsername());

        // 1. 校验账号是否存在 (查 user 表)和检查
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("username", registerDTO.getUsername());
        if (userMapper.selectCount(query) > 0) {
            log.warn("注册失败：用户名{} 已被使用", registerDTO.getUsername());
            return Result.error("账号已存在");
        }
        //  新增逻辑：校验手机号是否存在
        if (registerDTO.getMobile() != null && !registerDTO.getMobile().isEmpty()) {
            QueryWrapper<User> queryMobile = new QueryWrapper<>();
            queryMobile.eq("mobile", registerDTO.getMobile());
            if (userMapper.selectCount(queryMobile) > 0) {
                log.warn("注册失败：手机号 {} 已被使用", registerDTO.getMobile());
                return Result.error("该手机号已被注册");
            }
        }

        // 2. 插入 user 基础表
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(PasswordUtil.encrypt(registerDTO.getPassword()));
        user.setMobile(registerDTO.getMobile());
        user.setStatus(1);

        userMapper.insert(user);
        // Mybatis-Plus 会自动把生成的自增ID回填到 user.getId()
        Long newUserId = user.getId();
        log.info("基础用户创建成功，ID: {}", newUserId);

        // 3. 插入 customer_user 扩展表
        CustomerUser customer = new CustomerUser();
        customer.setUserId(newUserId); // 🌟 继承 ID
        // 如果注册DTO里没传昵称，默认用账号名
        customer.setNickname("用户" + registerDTO.getUsername());
        customer.setPoints(0); // 初始积分

        customerUserMapper.insert(customer);
        log.info("消费者档案创建成功");

        return Result.success("注册成功");
    }

    /**
     * 登录接口 (返回 基础信息 + 消费者信息)
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody UserLoginDTO loginDTO) {
        // 1. 校验账号密码 (查 user 表)
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("username", loginDTO.getUsername());
        User user = userMapper.selectOne(query);

        if (user == null || !PasswordUtil.match(loginDTO.getPassword(), user.getPassword())) {
            log.info("账号或密码错误，ID: {}", loginDTO.getUsername());
            return Result.error("账号或密码错误");
        }

        if (user.getStatus() == 0) {
            log.info("账号已禁用，ID: {}", loginDTO.getUsername());
            return Result.error("账号已禁用");
        }

        // 2. 查询消费者档案 (查 customer_user 表)
        CustomerUser customer = customerUserMapper.selectById(user.getId());

        // 3. 组装返回数据 (把两张表的数据合在一起返回)
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("userId", user.getId());
        responseData.put("username", user.getUsername());
        responseData.put("token", "fake-token-" + user.getId()); // 以后换成真实Token

        if (customer != null) {
            responseData.put("nickname", customer.getNickname());
            responseData.put("avatar", customer.getAvatarUrl());
            responseData.put("points", customer.getPoints());
        } else {
            // 极端情况：有账号但没消费者档案（可能是老数据或注册中断），可以在这里补救
            log.warn("用户 {} 缺少消费者档案", user.getId());
        }

        log.info("用户 {} 登录成功", user.getUsername());
        return Result.success(responseData);
    }

    // 1. 获取用户详情
    @GetMapping("/userDetail/{id}")
    public String getUser(@PathVariable int id) {
        //  替换了 System.out.println
        log.info("正在查询用户详情，接收到的ID: {}", id);
        return "查询到的用户ID为: " + id;
    }

    // 2. 更新用户
    @PostMapping("/updateUser")
    public String updateUser(@RequestBody UserUpdateDTO userDto) {
        //  替换了 System.out.println
        log.info("收到更新用户请求，数据: {}", userDto);
        return "更新成功: 姓名=" + userDto.getName() + ", 年龄=" + userDto.getAge();
    }

    @GetMapping("/1")
    public String user1() {
        return "这里是 user1 接口";
    }

    @GetMapping("/2")
    public String user2() {
        return "这里是 user2 接口";
    }
}