package com.lht.admin.controller.front;

import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.Tag;
import com.lht.admin.service.ITagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lht
 * @date 2021/4/21 - 0:12
 * @description: 前台博客页面标签控制器
 */
@RequestMapping("/front/tags")
@RestController
@Api(tags = "前端博客-文章标签")
public class FrontTagsController {

    @Resource
    private ITagService tagService;

    @GetMapping
    @ApiOperation(value = "获取所有标签")
    public List<Tag> getTagList() {
        return tagService.getAllTags();
    }
}
