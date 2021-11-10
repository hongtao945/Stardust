package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lht
 * @date 2021/10/27 - 14:15
 * @description: 留言查询数据传输对象
 */
@ApiModel("留言查询数据传输对象")
@Data
public class CommentOrMessageQueryDto {

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("开始创建日期")
    private String startTime;

    @ApiModelProperty("结束创建日期")
    private String endTime;
}
