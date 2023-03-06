package com.lht.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lht.admin.dto.TagQueryDto;
import com.lht.admin.pojo.*;
import com.lht.admin.service.ITagArticleService;
import com.lht.admin.service.ITagService;
import com.lht.annotation.SysLog;
import com.lht.utils.BatchRemoveUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Controller
@RequestMapping("/api/page/tag")
@Api(tags = "后台管理系统-文章标签管理")
public class TagController {

    @Resource
    private ITagService tagService;

    @Resource
    private ITagArticleService tagArticleService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到标签管理页面")
    public String index() {
        return "system/tag/tag";
    }

    @GetMapping
    @ApiOperation(value = "模糊查询标签列表")
    @ResponseBody
    @SysLog("模糊查询标签列表")
    public RespondPageBean fuzzyGetTagList(PageTableRequest pageRequest, TagQueryDto query) {
        return tagService.fuzzyGetTagList(pageRequest.getPage(), pageRequest.getLimit(), query);
    }

    @GetMapping("/add")
    @ApiOperation(value = "跳转到添加标签页面")
    public String toAddTag(Model model) {
        model.addAttribute("Tag",new Tag());
        return "system/tag/tag-add";
    }

    @PostMapping
    @ApiOperation(value = "添加标签")
    @ResponseBody
    @SysLog("添加标签")
    public ResponseBean addTag(@RequestBody Tag tag) {
        tag.setCreateTime(LocalDateTime.now());
        tag.setUpdateTime(LocalDateTime.now());
        return tagService.addTag(tag);
    }

    @GetMapping("/colors")
    @ApiOperation(value = "获取可选的标签颜色")
    @ResponseBody
    @SysLog("获取可选的标签颜色")
    public ResponseBean getColors() {
        return tagService.getColors();
    }

    @GetMapping("/edit")
    @ApiOperation(value = "跳转到编辑标签页面")
    @SysLog("跳转到编辑标签页面")
    public String toEditTag(Model model, Long tagId) {
        model.addAttribute("tag",tagService.getById(tagId));
        return "system/tag/tag-edit";
    }

    @PutMapping
    @ApiOperation(value = "更新一个标签")
    @ResponseBody
    @SysLog("更新一个标签")
    public ResponseBean update(@RequestBody Tag tag) {
        tag.setUpdateTime(LocalDateTime.now());
        if (tagService.updateById(tag)) {
            return ResponseBean.success("更新成功!");
        }
        return ResponseBean.error("更新失败!");
    }

    @DeleteMapping("/{tagId}")
    @ApiOperation(value = "通过id删除标签")
    @ResponseBody
    @SysLog("通过id删除标签")
    public ResponseBean deleteTagById(@PathVariable Long tagId) {
        if (tagArticleService.remove(new QueryWrapper<TagArticle>().eq("tag_id", tagId)) && tagService.removeById(tagId)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败！");
    }

    @DeleteMapping("batchRemove/{tagIds}")
    @ApiOperation(value = "批量删除标签")
    @ResponseBody
    @SysLog("批量删除标签")
    public ResponseBean batchRemove(@PathVariable String tagIds) {
        List<Long> tagList = BatchRemoveUtils.getLongTypeListByStr(tagIds);
        if (tagService.removeTagsById(tagList)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败！");
    }

    @GetMapping("/allTags")
    @ApiOperation(value = "获取所有标签")
    @ResponseBody
    @SysLog("获取所有标签")
    public ResponseBean getAllTags() {
        return ResponseBean.success("", tagService.list());
    }

}
