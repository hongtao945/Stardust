package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.ArticleQueryDto;
import com.lht.admin.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lht.admin.vo.ArticleDateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 分页模糊查询文章
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Article> fuzzyGetArticles(Page<Article> page, @Param("ew") QueryWrapper<Article> wrapper);

    /**
     * 前端通过id获取文章信息， 没有原生的文本内容， 可加入redis缓存
     * @param articleId
     * @return
     */
    Article getArticleInfoById(@Param("articleId") Long articleId);

    /**
     * 根据创建时间降序获取位置
     * @param page
     * @param tagId 标签id
     * @param classId 分类id
     * @return
     */
    IPage<Article> getArticlesSortedByDate(@Param("page") Page<Article> page, @Param("tagId") Long tagId, @Param("classId") Long classId);

    /**
     * 获取推荐文章
     * @return 推荐文章
     */
    List<Article> getRecommendArticles();

    /**
     * 上一篇文章
     * @param id 文章id
     * @return 文章
     */
    Article getPrevArticle(@Param("id") Long id);

    /**
     * 下一篇文章
     * @param id 文章id
     * @return 文章
     */
    Article getNextArticle(@Param("id") Long id);

    /**
     * 获取文章日创作频率表视图对象
     * @return 文章日创作频率表视图对象
     */
    List<ArticleDateVo> getArticleByDay();

    /**
     * 获取文章日创作频率表视图对象
     * @return 文章月创作频率表视图对象
     */
    List<ArticleDateVo> getArticleByMonth();

    /**
     * 浏览量增加一
     * @param id 文章id
     */
    void viewsIncrement(@Param("id") Long id);

    /**
     * 后台编辑文章时通过id获取文章， 没有html内容， 不加入缓存
     * @param articleId
     * @return
     */
    Article getArticleById(Long articleId);
}
