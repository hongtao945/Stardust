package com.lht.admin.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lht.admin.dto.CommentOrMessageQueryDto;
import com.lht.admin.pojo.*;
import com.lht.admin.service.ICommentService;
import com.lht.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Controller
@RequestMapping("/api/page/comment")
@Api(tags = "后台管理系统-评论管理")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到评论管理页面")
    public String index() {
        return "system/comment/comment";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "分页获取留言")
    @SysLog("分页获取留言")
    public RespondPageBean fuzzyGetMessageList(PageTableRequest request, CommentOrMessageQueryDto commentOrMessageQueryDto) {
        return commentService.fuzzyGetMessageList(request.getPage(), request.getLimit(), commentOrMessageQueryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "删除留言")
    @SysLog("删除留言")
    public ResponseBean deleteById(@PathVariable("id") Long id) {
        UpdateWrapper<Comment> wrapper = new UpdateWrapper<>();
        wrapper.eq("message_id", id).set("is_delete", true).set("content", "该留言已被管理员删除！");
        if (commentService.update(wrapper)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }

}
