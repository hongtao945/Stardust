package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author lht
 * @date 2021/11/5 - 13:06
 * @description: 添加用户数据传输对象
 */
@Data
@ApiModel(value="添加用户数据传输对象")
public class UserAddDto {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "角色id列表")
    private List<Long> roleIds;
}
