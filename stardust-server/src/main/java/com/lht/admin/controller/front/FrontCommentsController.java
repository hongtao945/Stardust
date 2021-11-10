package com.lht.admin.controller.front;

import com.lht.admin.dto.CommentDto;
import com.lht.admin.pojo.Comment;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.ICommentService;
import com.lht.annotation.SysLog;
import com.lht.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Author lht
 * @date 2021/10/22 - 11:33
 * @description: 前端博客文章评论控制器
 */
@RestController
@RequestMapping("/front/comments/{id}")
@Api(tags = "前端博客-评论")
public class FrontCommentsController {

    @Resource
    private ICommentService commentService;

    @GetMapping
    @ApiOperation(value = "根据文章id获取对应的评论")
    public RespondPageBean getCommentsByArticleId(PageTableRequest request, @PathVariable("id") Long id) {
        return commentService.getCommentsByArticleId(request.getPage(), request.getLimit(), id);
    }

    @PostMapping
    @ApiOperation(value = "为文章添加一条评论")
    public ResponseBean addComments(Comment comment, @PathVariable("id") Long id, HttpServletRequest request) {
        comment.setRequestIp(StringUtils.getIp(request));
        comment.setAddress(StringUtils.getCityInfo(comment.getRequestIp()));
        comment.setCreateTime(LocalDateTime.now());
        comment.setOs(StringUtils.getClientOS(request));
        comment.setArticleId(id);
        comment.setBrowser(StringUtils.getBrowser(request));
        if (commentService.save(comment)) {
            return ResponseBean.success("添加成功!");
        }
        return ResponseBean.error("添加失败");
    }

}
