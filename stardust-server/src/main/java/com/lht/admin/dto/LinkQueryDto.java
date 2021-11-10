package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lht
 * @date 2021/4/3 - 16:15
 * @description: 友链搜索数据传输对象
 */
@Data
@ApiModel(value = "友链搜索数据传输对象")
public class LinkQueryDto implements Serializable {
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("审核状态")
    private Integer status;
    @ApiModelProperty("开始创建日期")
    private String startTime;
    @ApiModelProperty("结束创建日期")
    private String endTime;
}
