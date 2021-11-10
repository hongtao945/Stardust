package com.lht.admin.controller.front;

import com.lht.admin.pojo.*;
import com.lht.admin.service.ILinkService;
import com.lht.admin.service.IMessageService;
import com.lht.annotation.SysLog;
import com.lht.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author lht
 * @date 2021/10/24 - 23:00
 * @description: 前端博客页面友链控制器
 */
@Api(tags = "前端博客-友链")
@RestController
@RequestMapping("/front/friends")
public class FrontFriendsController {

    @Resource
    private ILinkService linkService;

    @Resource
    private IMessageService messageService;

    @GetMapping
    public List<Link> getFriendsLink() {
        return linkService.getFriendLink();
    }

    @PostMapping
    @ApiOperation(value = "添加一条留言")
    public ResponseBean addMessages(Message message, HttpServletRequest request) {
        message.setRequestIp(StringUtils.getIp(request));
        message.setAddress(StringUtils.getCityInfo(message.getRequestIp()));
        message.setCreateTime(LocalDateTime.now());
        message.setOs(StringUtils.getClientOS(request));
        message.setBrowser(StringUtils.getBrowser(request));
        if (messageService.save(message)) {
            return ResponseBean.success("添加成功!");
        }
        return ResponseBean.error("添加失败!");
    }

    @GetMapping("/msg")
    @ApiOperation(value = "获取留言")
    public RespondPageBean getMessages(PageTableRequest request) {
        return messageService.getMessages(request.getPage(), request.getLimit());
    }


}
