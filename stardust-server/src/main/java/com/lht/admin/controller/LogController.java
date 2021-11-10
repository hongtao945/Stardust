package com.lht.admin.controller;


import com.lht.admin.dto.LogQueryDto;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.ILogService;
import com.lht.annotation.SysLog;
import com.lht.utils.BatchRemoveUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Controller
@RequestMapping("/api/log")
@Api(tags = "后台管理系统-日志管理")
public class LogController {

    @Resource
    private ILogService logService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到日志管理页面")
    public String index() {
        return "system/log/log";
    }

    @GetMapping("/error/index")
    @ApiOperation(value = "跳转到日志管理页面")
    public String errorIndex() {
        return "system/log/errorLog";
    }

    @GetMapping
    @ApiOperation(value = "模糊查询普通日志列表")
    @ResponseBody
    @SysLog("模糊查询普通日志列表")
    public RespondPageBean fuzzyGetLogList(PageTableRequest request, LogQueryDto logQueryDto) {
        return logService.fuzzyGetLogList(request.getPage(), request.getLimit(), logQueryDto, "INFO");
    }

    @GetMapping("/error")
    @ApiOperation(value = "模糊查询错误日志列表")
    @ResponseBody
    @SysLog("模糊查询错误日志列表")
    public RespondPageBean fuzzyGetErrorLogList(PageTableRequest request, LogQueryDto logQueryDto) {
        return logService.fuzzyGetLogList(request.getPage(), request.getLimit(), logQueryDto, "ERROR");
    }

    @DeleteMapping("/{operId}")
    @ApiOperation(value = "删除一条日志")
    @ResponseBody
    @SysLog("删除一条日志")
    public ResponseBean deleteByLogId(@PathVariable Long operId) {
        logService.removeById(operId);
        return ResponseBean.success("删除成功！");
    }

    @DeleteMapping("/batchRemove/{ids}")
    @ApiOperation(value = "批量删除日志")
    @ResponseBody
    @SysLog("批量删除日志")
    public ResponseBean batchRemoveLogs(@PathVariable String ids) {
        List<Long> idList = BatchRemoveUtils.getLongTypeListByStr(ids);
        logService.removeByIds(idList);
        return ResponseBean.success("删除成功！");
    }

    @DeleteMapping("/allErrorLog")
    @ApiOperation(value = "删除所有错误日志")
    @ResponseBody
    @SysLog("删除所有错误日志")
    public ResponseBean removeAllErrorLogs() {
        if (logService.deleteAllErrorLog()) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }

    @DeleteMapping("/allInfoLog")
    @ApiOperation(value = "删除所有普通日志")
    @ResponseBody
    @SysLog("删除所有普通日志")
    public ResponseBean removeAllInfoLogs() {
        if (logService.deleteAllInfoLog()) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }

}
