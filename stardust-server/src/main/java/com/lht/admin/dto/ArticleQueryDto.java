package com.lht.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lht
 * @date 2021/3/29 - 14:06
 * @description: 文章查询数据传输对象
 */
@Data
@ApiModel("文章查询条件")
public class ArticleQueryDto implements Serializable {
    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("类型[1:原创, 2:转载, 3:翻译]")
    private Integer type;

    @ApiModelProperty("分类ID")
    private Long classId;

    @ApiModelProperty("是否发布")
    private Boolean published;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    private Integer status;

    @ApiModelProperty("是否推荐")
    private Boolean recommend;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;
}
