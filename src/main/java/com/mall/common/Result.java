package com.mall.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code; // 200 表示成功
    private String msg;   // "success" 或 错误信息
    private T data;       // 具体的数据对象

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.code = 200;
        r.msg = "success";
        r.data = data;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.code = 500;
        r.msg = msg;
        return r;
    }
}
