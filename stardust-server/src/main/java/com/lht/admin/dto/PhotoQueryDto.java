package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lht
 * @date 2021/4/4 - 14:46
 * @description: 图片搜索数据传输对象
 */
@ApiModel("图片搜索数据传输对象")
@Data
public class PhotoQueryDto implements Serializable {
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("开始创建日期")
    private String startTime;

    @ApiModelProperty("结束创建日期")
    private String endTime;

    @ApiModelProperty("是否前台显示")
    private Boolean display;
}
