package com.lht.admin.service;

import com.lht.admin.pojo.Article;
import com.lht.admin.pojo.EsArticleModel;

import java.util.List;

/**
 * @Author lht
 * @date 2021/10/25 - 19:54
 * @description: es检索服务
 */
public interface IElasticSearchService {

    /**
     * 根据关键字检索
     * @param keyword 关键字
     * @return 检索结果
     */
    List<EsArticleModel> search(String keyword);

    /**
     * 添加一份文档数据
     * @param article 文章对象
     */
    void save(Article article);

    /**
     * 根据id删除文档
     * @param id
     */
    void deleteById(Long id);

    /**
     * 删除es索引中现有的文档
     */
    void deleteAll();

    /**
     * 将数据库中已有的文章保存到es中
     */
    void sync();

}
