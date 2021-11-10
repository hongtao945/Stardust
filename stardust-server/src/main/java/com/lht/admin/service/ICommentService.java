package com.lht.admin.service;

import com.lht.admin.dto.CommentOrMessageQueryDto;
import com.lht.admin.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.vo.ConsoleCommentsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 分页获取文章评论
     * @param offset
     * @param limit
     * @param id
     * @return 文章评论
     */
    RespondPageBean getCommentsByArticleId(Integer offset, Integer limit, Long id);

    /**
     * 后台分页模糊查询评论列表
     * @param offset
     * @param limit
     * @param commentOrMessageQueryDto
     * @return 评论列表
     */
    RespondPageBean fuzzyGetMessageList(Integer offset, Integer limit, CommentOrMessageQueryDto commentOrMessageQueryDto);

    /**
     * 获取最新的10条评论
     * @return
     */
    List<ConsoleCommentsVo> getNewestComments();
}
