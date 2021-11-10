package com.lht.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>
 *
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_article")
@ApiModel(value="Article对象", description="")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "html内容")
    private String htmlContent;

    @ApiModelProperty(value = "markdown内容")
    private String textContent;

    @ApiModelProperty(value = "封面地址")
    private String articleFace;

    @ApiModelProperty(value = "1：原创 2：转载 3：翻译")
    private Integer type;

    @ApiModelProperty(value = "文章字数")
    private Integer words;

    @ApiModelProperty(value = "评论量")
    @TableField(exist = false)
    private Integer comments;

    @ApiModelProperty(value = "访问量")
    private Integer views;

    @ApiModelProperty(value = "开启推荐")
    private Boolean recommend;

    @ApiModelProperty(value = "是否发布")
    private Boolean published;

    @ApiModelProperty(value = "排序评分")
    private Integer sort;

    @ApiModelProperty(value = "作者id")
    private Long authorId;

    @ApiModelProperty(value = "分类id")
    private Long classId;

    @ApiModelProperty(value = "审核状态 0：未过 1：等待 2：过")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("分类")
    @TableField(exist = false)
    private Classification classification;

    @ApiModelProperty("标签列表")
    @TableField(exist = false)
    private List<Tag> tagList;

    @ApiModelProperty("作者")
    @TableField(exist = false)
    private User author;

    @AllArgsConstructor
    @Getter
    public enum StatusEnum {
        WAIT(1),
        NO_PASS(0),
        PASS(2);
        private Integer code;
    }

    @AllArgsConstructor
    @Getter
    public enum PublishEnum {
        YES(true),
        NO(false);
        private Boolean flag;
    }

}
