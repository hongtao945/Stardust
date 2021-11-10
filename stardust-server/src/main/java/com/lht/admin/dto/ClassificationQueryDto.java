package com.lht.admin.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lht
 * @date 2021/10/27 - 22:26
 * @description: 分类查询数据传输对象
 */
@Data
@ApiModel("分类查询数据传输对象")
public class ClassificationQueryDto {

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("是否前台展示")
    private Boolean display;

    @ApiModelProperty("开始创建日期")
    private String startTime;

    @ApiModelProperty("结束创建日期")
    private String endTime;

}
