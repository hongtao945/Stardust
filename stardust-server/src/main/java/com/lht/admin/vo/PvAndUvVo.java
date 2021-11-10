package com.lht.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lht
 * @date 2021/10/30 - 18:00
 * @description: 用户访问量和页面访问量
 */
@Data
@ApiModel(value = "用户访问量和页面访问量")
public class PvAndUvVo {

    @ApiModelProperty(value = "页面服务类")
    private Long pv;

    @ApiModelProperty(value = "用户访问量")
    private Long uv;

}
