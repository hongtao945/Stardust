package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.CommentOrMessageQueryDto;
import com.lht.admin.pojo.Comment;
import com.lht.admin.mapper.CommentMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.admin.vo.ConsoleCommentsVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public RespondPageBean getCommentsByArticleId(Integer offset, Integer limit, Long id) {
        Page<Comment> page = new Page<>(offset, limit);
        IPage<Comment> res = commentMapper.getParentComments(page, id);
        List<Comment> allComments = commentMapper.getAllCommentsOfArticle(id);
        res.getRecords().stream().forEach(parent -> {
            parent.setChildren(new ArrayList<>());
            buildCommentsTree(parent, allComments, parent.getChildren());
        });
        return new RespondPageBean(res.getTotal(), res.getRecords());
    }

    /**
     * 递归构建评论
     * @param parent 父级评论
     * @param allComments 所有可能的子评论
     * @param children 子评论
     */
    private void buildCommentsTree(Comment parent, List<Comment> allComments, List<Comment> children) {
        allComments.stream()
                .filter(item -> Objects.equals(item.getFid(), parent.getCommentId()))
                .forEach(item -> {
                    children.add(item);
                    buildCommentsTree(item, allComments, children);
                });
    }

    @Override
    public RespondPageBean fuzzyGetMessageList(Integer offset, Integer limit, CommentOrMessageQueryDto commentOrMessageQueryDto) {
        Page<Comment> page = new Page<>(offset, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        if (!StringUtils.isEmpty(commentOrMessageQueryDto.getNickname())) {
            wrapper.like("nickname", commentOrMessageQueryDto.getNickname());
        }
        if (!StringUtils.isEmpty(commentOrMessageQueryDto.getStartTime()) && !StringUtils.isEmpty(commentOrMessageQueryDto.getEndTime())) {
            wrapper.between("create_time", commentOrMessageQueryDto.getStartTime(), commentOrMessageQueryDto.getEndTime());
        }
        IPage<Comment> res = commentMapper.fuzzyGetMessageList(page, wrapper);
        return new RespondPageBean(res.getTotal(), res.getRecords());
    }

    @Override
    public List<ConsoleCommentsVo> getNewestComments() {
        return commentMapper.getNewestComments();
    }

}
