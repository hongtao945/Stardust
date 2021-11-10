package com.lht.constant;

/**
 * @Author lht
 * @date 2021/10/25 - 21:10
 * @description: es常量
 */
public interface EsConstant {
    /**
     * es中索引的名称
     */
    String INDEX_NAME = "article_document";
    String ARTICLE_ID = "articleId";
    String TITLE = "title";
    String SUMMARY = "summary";
    String HTML_CONTENT = "htmlContent";
    String PUBLISHED = "published";
    String STATUS = "status";
    String HIGHLIGHT_PRE_TAGS = "<em class='search-keyword'>";
    String HIGHLIGHT_POST_TAGS = "</em>";
}
