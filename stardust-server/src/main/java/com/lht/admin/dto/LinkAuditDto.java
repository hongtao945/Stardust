package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lht
 * @date 2021/4/3 - 17:13
 * @description: 友链审核数据传输对象
 */
@ApiModel("友链审核数据传输对象")
@Data
public class LinkAuditDto {
    @ApiModelProperty("友链id")
    @NotNull
    private Long LinkId;

    @ApiModelProperty("审核状态")
    @NotNull
    private Integer status;
}
