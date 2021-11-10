package com.lht.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author lht
 * @date 2021/10/19 - 18:06
 * @description: 文章视图对象
 */
@Data
public class ArticleVo {
    private Long articleId;
    private List<String> tagNames;
    private String categoryName;
    private String title;
    private String summary;
    private String htmlContent;
    private String textContent;
    private String articleFace;
    private Integer type;
    private Integer words;
    private Integer comments;
    private Integer views;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
