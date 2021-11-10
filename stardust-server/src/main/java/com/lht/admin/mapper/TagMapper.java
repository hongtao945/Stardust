package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.TagQueryDto;
import com.lht.admin.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface TagMapper extends BaseMapper<Tag> {


    /**
     * 根据文章id查询标签列表
     * @param articleId 文章id
     * @return 标签列表
     */
    List<Tag> selectByArticleId(@Param("articleId") long articleId);

    IPage<Tag> getFrontTagList(Page<Tag> page);

    /**
     * 获取所有标签
     * @return 所有标签
     */
    List<Tag> getAllTags();

    /**
     * 模糊查询标签列表
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Tag> fuzzyGetTagList(@Param("page") Page<Tag> page, @Param("ew") QueryWrapper<Tag> wrapper);

    /**
     * 获取已关联文章的标签数目总数
     * @return
     */
    Integer getCount();
}
