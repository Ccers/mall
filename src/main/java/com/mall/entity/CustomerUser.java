package com.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer_user")
public class CustomerUser {

    // 重点：这里不是自增(AUTO)，而是 INPUT。
    // 因为它的ID必须和 user 表生成的ID完全一致。
    @TableId(type = IdType.INPUT)
    private Long userId;

    private String nickname;
    private String avatarUrl;
    // ... 其他字段可以按需添加，这里先列出核心的
    private Integer points;
}