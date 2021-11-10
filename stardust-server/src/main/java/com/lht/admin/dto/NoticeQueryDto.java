package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lht
 * @date 2021/11/2 - 21:23
 * @description: 公告查询数据传输对象
 */
@Data
@ApiModel(value = "公告查询数据传输对象")
public class NoticeQueryDto {

    @ApiModelProperty("公告内容")
    private String content;

    @ApiModelProperty("开始创建日期")
    private String startTime;

    @ApiModelProperty("结束创建日期")
    private String endTime;

}
