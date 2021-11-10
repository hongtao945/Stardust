package com.lht.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author lht
 * @date 2021/10/23 - 14:27
 * @description: 归档页面文章创作频率表视图对象
 */
@Data
@ApiModel(description = "归档页面文章创作频率表视图对象")
public class ArticleDateVo {

    @ApiModelProperty(value = "创作数")
    private Integer count;

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;
}
