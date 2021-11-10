package com.lht.admin.controller;


import com.lht.admin.dto.LinkAuditDto;
import com.lht.admin.dto.LinkQueryDto;
import com.lht.admin.pojo.Link;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.ILinkService;
import com.lht.annotation.SysLog;
import com.lht.utils.BatchRemoveUtils;
import com.lht.constant.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Api(tags = "后台管理系统-友链管理")
@Controller
@RequestMapping("/api/page/link")
public class LinkController {

    @Resource
    private ILinkService linkService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到友链管理页面")
    public String index() {
        return "system/link/link";
    }

    @GetMapping
    @ApiOperation(value = "模糊查询友链列表")
    @ResponseBody
    @SysLog("模糊查询友链列表")
    public RespondPageBean fuzzyGetLinkList(PageTableRequest request, LinkQueryDto queryDto) {
        return linkService.fuzzyGetLinkList(request.getPage(), request.getLimit(), queryDto);
    }

    @GetMapping("/add")
    @ApiOperation(value = "打开添加友链界面")
    @SysLog("打开添加友链界面")
    public String toAddLink() {
        return "system/link/link-add";
    }

    @GetMapping("/edit")
    @ApiOperation(value = "打开更新友链页面")
    @SysLog("打开更新友链页面")
    public String toEditLink(Model model, Long linkId) {
        model.addAttribute("link", linkService.getById(linkId));
        return "system/link/link-edit";
    }

    @GetMapping("/audit")
    @ApiOperation(value = "打开审核页面")
    @SysLog("打开审核页面")
    public String toAuditLink(Model model, Long linkId) {
        model.addAttribute("link", linkService.getById(linkId));
        return "system/link/link-audit";
    }

    @PostMapping
    @ApiOperation(value = "添加一个友链")
    @ResponseBody
    @SysLog("添加一个友链")
    public ResponseBean save(@RequestBody Link link) {
        link.setCreateTime(LocalDateTime.now());
        link.setUpdateTime(LocalDateTime.now());
        link.setStatus(Constant.AUDIT_WAIT);
        if (linkService.save(link)) {
            return ResponseBean.success("添加成功!");
        }
        return ResponseBean.error("添加失败!");
    }

    @PutMapping
    @ApiOperation(value = "更新一个友链")
    @ResponseBody
    @SysLog("更新一个友链")
    public ResponseBean update(@RequestBody Link link) {
        link.setUpdateTime(LocalDateTime.now());
        link.setStatus(Constant.AUDIT_WAIT);
        if (linkService.updateById(link)) {
            return ResponseBean.success("更新成功!");
        }
        return ResponseBean.error("失败!");
    }

    @DeleteMapping("/{linkId}")
    @ApiOperation(value = "删除友链")
    @ResponseBody
    @SysLog("删除友链")
    public ResponseBean deleteLinkById(@PathVariable Long linkId) {
        if (linkService.removeById(linkId)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }

    @DeleteMapping("batchRemove/{linkIds}")
    @ApiOperation(value = "批量删除友链")
    @ResponseBody
    @SysLog("批量删除友链")
    public ResponseBean batchRemoveLink(@PathVariable String linkIds) {
        List<Long> linkList = BatchRemoveUtils.getLongTypeListByStr(linkIds);
        if (linkService.removeByIds(linkList)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }

    @PostMapping("/audit")
    @ApiOperation(value = "审核友链")
    @ResponseBody
    @SysLog("审核友链")
    public ResponseBean audit(@RequestBody LinkAuditDto auditDto) {
        return linkService.audit(auditDto);
    }

}
