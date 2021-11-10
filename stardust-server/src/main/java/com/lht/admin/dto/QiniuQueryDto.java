package com.lht.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lht
 * @date 2021/4/1 - 20:56
 * @description: 七牛云查询数据传输对象
 */
@Data
public class QiniuQueryDto implements Serializable {
    public String name;
    public String startTime;
    public String endTime;
}
