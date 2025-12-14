package com.mall.mapper;

import com.mall.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 里面是空的，但它已经拥有了 insert, selectById, selectList 等方法
}