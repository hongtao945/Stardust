package com.lht.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 * @Author lht
 * @date 2021/3/20 - 20:04
 * @description:
 */
@Data
@AllArgsConstructor
public class ResponseBean implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    private ResponseBean(){}

    public static ResponseBean success(String message,Object data) {
        return new ResponseBean(200, message, data);
    }

    public static ResponseBean success(String message) {
        return success(message, null);
    }

    public static ResponseBean error(String message, Object data) {
        return new ResponseBean(500, message, data);
    }

    public static ResponseBean error(String message) {
        return error(message, null);
    }
}
