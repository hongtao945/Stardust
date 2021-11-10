package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lht.admin.vo.ConsoleCommentsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据文章id分页获取父级评论， 即pid=0的评论
     * @param page
     * @param id
     * @return
     */
    IPage<Comment> getParentComments(Page<Comment> page, @Param("id") Long id);

    /**
     * 获取一篇文章所有的评论
     * @param id
     * @return
     */
    List<Comment> getAllCommentsOfArticle(@Param("id") Long id);

    /**
     * 后台分页模糊查询评论列表
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Comment> fuzzyGetMessageList(@Param("page") Page<Comment> page, @Param("ew") QueryWrapper<Comment> wrapper);

    /**
     * 获取最新的10条评论
     * @return
     */
    List<ConsoleCommentsVo> getNewestComments();
}
