package com.lht.admin.vo;

import com.lht.admin.pojo.Classification;
import com.lht.admin.pojo.Photo;
import com.lht.admin.pojo.Tag;
import lombok.Data;

import java.util.List;

/**
 * @Author lht
 * @date 2021/10/24 - 14:34
 * @description: 前台博客关于我页面视图对象
 */
@Data
public class AboutVo {
    private Integer articleCount;
    private Integer classificationCount;
    private Integer tagCount;
    private List<Classification> classifications;
    private List<Tag> tags;
    private List<ArticleDateVo> articleDateVoList;
}
