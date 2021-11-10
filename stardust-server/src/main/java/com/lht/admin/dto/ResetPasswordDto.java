package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lht
 * @date 2021/11/3 - 18:26
 * @description: 修改密码数据传输对象
 */
@Data
@ApiModel(value = "修改密码数据传输对象")
public class ResetPasswordDto {

    @ApiModelProperty(value = "旧密码")
    String oldPassword;

    @ApiModelProperty(value = "新密码")
    String newPassword;
}
