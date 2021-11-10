package com.lht.admin.dto;

import com.lht.admin.pojo.RespondPageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lht
 * @date 2021/4/6 - 18:36
 * @description: 日志查询数据传输对象
 */
@Data
@ApiModel("日志查询条件")
public class LogQueryDto implements Serializable {
    @ApiModelProperty("操作的用户")
    private String Operator;

    @ApiModelProperty("操作描述")
    private String title;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;

}
