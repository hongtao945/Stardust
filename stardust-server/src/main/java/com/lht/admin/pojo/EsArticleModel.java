package com.lht.admin.pojo;

import lombok.Data;

/**
 * @Author lht
 * @date 2021/10/25 - 18:12
 * @description: es中保存的数据模型, 属性参照{@link com.lht.admin.pojo.Article}
 */
@Data
public class EsArticleModel {

    /**
     * 文章id， 返回数据后可以从mysql数据库中得到文章的详细信息
     */
    private Long articleId;

    /**
     * 文章的标题， 作为检索的条件之一
     */
    private String title;

    /**
     * 文章的摘要， 作为检索的条件之一
     */
    private String summary;

    /**
     * 文本内容， 作为全文检索的条件之一
     */
    private String htmlContent;

    /**
     * 是否已发布
     */
    private Boolean published;

    /**
     * 是否通过审核
     */
    private Integer status;

}
