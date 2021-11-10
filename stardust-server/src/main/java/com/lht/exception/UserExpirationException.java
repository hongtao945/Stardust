package com.lht.exception;

import lombok.Data;

/**
 * @Author lht
 * @date 2021/4/5 - 15:53
 * @description: 用户登录过期异常类
 */
@Data
public class UserExpirationException extends RuntimeException{
    private Integer code;
    private String msg;

    public UserExpirationException() {
        this.code = 401;
        this.msg = "用户登录已过期";
    }
}
