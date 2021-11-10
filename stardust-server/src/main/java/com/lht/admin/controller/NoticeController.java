package com.lht.admin.controller;


import com.lht.admin.dto.NoticeQueryDto;
import com.lht.admin.pojo.*;
import com.lht.admin.service.INoticeService;
import com.lht.annotation.SysLog;
import com.lht.security.LoginUser;
import com.lht.utils.BatchRemoveUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 通知公告表 前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Controller
@RequestMapping("/api/notice")
@Api(tags = "后台管理系统-公告管理")
public class NoticeController {

    @Resource
    private INoticeService noticeService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到公告管理页面")
    public String index() {
        return "system/notice/notice";
    }

    @GetMapping("/add")
    @ApiOperation(value = "跳转到添加公告页面")
    public String add() {
        return "system/notice/notice-add";
    }

    @GetMapping("/edit")
    @ApiOperation(value = "跳转到编辑公告页面")
    @SysLog("跳转到编辑公告页面")
    public String edit(Model model, Long noticeId) {
        model.addAttribute("notice", noticeService.getById(noticeId));
        return "system/notice/notice-edit";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "模糊查询公告列表")
    @SysLog("模糊查询公告列表")
    public RespondPageBean fuzzyGetNoticeList(PageTableRequest pageRequest, NoticeQueryDto queryDto) {
        return noticeService.fuzzyGetNoticeList(pageRequest.getPage(), pageRequest.getLimit(), queryDto);
    }

    @ResponseBody
    @PostMapping
    @ApiOperation(value = "添加一则公告")
    @SysLog("添加一则公告")
    @PreAuthorize("hasAnyAuthority('notice:add')")
    public ResponseBean addNotice(@RequestBody Notice notice) {
        notice.setCreateTime(LocalDateTime.now());
        notice.setCreateBy(((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getNickname());
        if (noticeService.save(notice)) {
            return ResponseBean.success("添加成功!");
        }
        return ResponseBean.error("添加失败!");
    }

    @ResponseBody
    @PutMapping
    @ApiOperation(value = "更新一则公告")
    @SysLog("更新一则公告")
    @PreAuthorize("hasAnyAuthority('notice:edit')")
    public ResponseBean updateNotice(@RequestBody Notice notice) {
        if (noticeService.updateById(notice)) {
            return ResponseBean.success("更新成功!");
        }
        return ResponseBean.error("更新失败!");
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除一则公告")
    @SysLog("删除一则公告")
    @PreAuthorize("hasAnyAuthority('notice:delete')")
    public ResponseBean deleteNotice(@PathVariable("id") Long id) {
        if (noticeService.removeById(id)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }

    @DeleteMapping("batchRemove/{ids}")
    @ApiOperation(value = "批量删除公告")
    @ResponseBody
    @SysLog("批量删除公告")
    @PreAuthorize("hasAnyAuthority('notice:delete')")
    public ResponseBean batchRemoveFile(@PathVariable String ids) {
        List<Long> idList = BatchRemoveUtils.getLongTypeListByStr(ids);
        if (noticeService.removeByIds(idList)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败！");
    }
}
