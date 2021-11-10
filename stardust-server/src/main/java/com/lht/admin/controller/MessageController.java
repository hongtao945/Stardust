package com.lht.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lht.admin.dto.CommentOrMessageQueryDto;
import com.lht.admin.pojo.Message;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.IMessageService;
import com.lht.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author lht
 * @date 2021/10/27 - 14:07
 * @description: 留言控制器
 */

@Controller
@RequestMapping("/api/page/message")
@Api(tags = "后台管理系统-留言管理")
public class MessageController {

    @Resource
    private IMessageService messageService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到评论管理页面")
    public String index() {
        return "system/message/message";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "分页获取留言")
    @SysLog("分页获取留言")
    public RespondPageBean fuzzyGetMessageList(PageTableRequest request, CommentOrMessageQueryDto commentOrMessageQueryDto) {
        return messageService.fuzzyGetMessageList(request.getPage(), request.getLimit(), commentOrMessageQueryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "删除留言")
    @SysLog("删除留言")
    public ResponseBean deleteById(@PathVariable("id") Long id) {
        UpdateWrapper<Message> wrapper = new UpdateWrapper<>();
        wrapper.eq("message_id", id).set("is_delete", true).set("content", "该留言已被管理员删除！");
        if (messageService.update(wrapper)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }

}
