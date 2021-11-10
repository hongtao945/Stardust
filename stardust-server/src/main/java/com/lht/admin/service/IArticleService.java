package com.lht.admin.service;

import com.lht.admin.dto.ArticleAuditDto;
import com.lht.admin.dto.ArticleQueryDto;
import com.lht.admin.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.vo.ArticleDateVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface IArticleService extends IService<Article> {

    /**
     * 新增一篇文章
     * @param article
     * @return
     */
    ResponseBean SaveOrUpdate(Article article);

    /**
     * 模糊查询所有文章
     * @param offset
     * @param limit
     * @param articleQueryDto
     * @return
     */
    RespondPageBean fuzzyGetArticles(Integer offset, Integer limit, ArticleQueryDto articleQueryDto);

    /**
     * 通过id获取文章信息
     * @param articleId
     * @return
     */
    Article getArticleInfoById(Long articleId);

    /**
     * 根据创建日期降序获取文章
     * @param offset 当前第几页
     * @param limit 一页多少数据
     * @param tagId 标签id
     * @param classId 分类id
     * @return 文章
     */
    RespondPageBean getArticlesSortedByDate(Integer offset, Integer limit, Long tagId, Long classId);

    /**
     * 获取推荐文章
     * @return 推荐文章
     */
    List<Article> getRecommendArticles();

    /**
     * 获取一篇文章的上一篇和下一篇文章
     * @param id 文章id
     * @return 第0位：上一篇 第1位：下一篇
     */
    List<Article> getPrevAndNextArticle(Long id);

    /**
     * 获取文章日创作频率表视图对象
     * @return 文章日创作频率表视图对象
     */
    List<ArticleDateVo> getArticleByDay();

    /**
     * 获取文章月创作频率表视图对象
     * @return 文章月创作频率表视图对象
     */
    List<ArticleDateVo> getArticleByMonth();

    /**
     * 获取文章数目
     * @return 文章数目
     */
    Integer getCount();

    /**
     * 浏览量增加一
     * @param id 文章id
     */
    void viewsIncrement(Long id);

    /**
     * 删除一篇文章
     * @param id 文章id
     * @return 是否删除成功
     */
    boolean delete(Long id);

    /**
     * 审核文章
     * @param articleAuditDto 传输的数据
     * @return 是否成功
     */
    boolean audit(ArticleAuditDto articleAuditDto);
}
