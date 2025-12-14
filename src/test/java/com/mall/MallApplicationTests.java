package com.mall;

import com.mall.mapper.UserMapper;
import com.mall.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testConnection() {
        System.out.println(("----- 开始测试数据库连接 -----"));
        // 查询所有用户
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        System.out.println(("----- 连接成功 -----"));
    }
}
