package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lht
 * @date 2021/11/5 - 17:18
 * @description: 文章审核数据传输对象
 */
@Data
@ApiModel(value = "文章审核数据传输对象")
public class ArticleAuditDto {

    @ApiModelProperty(value = "文章id")
    private Long articleId;

    @ApiModelProperty(value = "审核状态")
    private Integer status;
}
