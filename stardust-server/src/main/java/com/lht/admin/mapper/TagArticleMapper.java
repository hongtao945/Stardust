package com.lht.admin.mapper;

import com.lht.admin.pojo.TagArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TagArticleMapper extends BaseMapper<TagArticle> {

    /**
     * 批量插入文章标签
     * @param articleId
     * @param tagList
     */
    void insertBatch(@Param("articleId") Long articleId, @Param("tagList") List<Long> tagList);
}
