package com.lht.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author lht
 * @date 2021/11/4 - 18:52
 * @description: 控制台页面评论视图对象
 */
@Data
@ApiModel(value = "控制台页面评论视图对象")
public class ConsoleCommentsVo {

    @ApiModelProperty(value = "评论的昵称")
    private String nickname;

    @ApiModelProperty(value = "评论的内容")
    private String content;

    @ApiModelProperty(value = "所属文章的标题")
    private String articleTitle;

    @ApiModelProperty(value = "ip来源")
    private String address;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
