package com.lht.admin.controller;


import com.lht.admin.dto.QiniuQueryDto;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.QiniuConfig;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.IQiniuConfigService;
import com.lht.admin.service.IQiniuContentService;
import com.lht.annotation.SysLog;
import com.lht.utils.BatchRemoveUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-31
 */
@Controller
@RequestMapping("/api/qiniu")
@Api(tags = "后台管理系统-七牛云")
public class QiniuController {

    @Resource
    private IQiniuConfigService qiniuConfigService;
    @Resource
    private IQiniuContentService qiniuContentService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到七牛云管理页面")
    public String index() {
        return "system/qiniu/qiniu";
    }

    @GetMapping("/add")
    @ApiOperation(value = "打开上传文件界面")
    public String toUploadFile() {
        return "system/qiniu/qiniu-add-file";
    }

    @DeleteMapping("batchRemove/{ids}")
    @ApiOperation(value = "批量删除文件")
    @ResponseBody
    @SysLog("批量删除文件")
    public ResponseBean batchRemoveFile(@PathVariable String ids) {
        List<Integer> idList = BatchRemoveUtils.getIntegerTypeListByStr(ids);
        if (qiniuContentService.batchRemoveFile(idList, qiniuConfigService.getById(1))) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败！");
    }

    @GetMapping
    @ApiOperation(value = "模糊查询七牛云中的图片数据")
    @ResponseBody
    @SysLog("模糊查询七牛云中的图片数据")
    public RespondPageBean getPhotoContent(PageTableRequest request, QiniuQueryDto qiniuQueryDto) {
        return qiniuContentService.getPhotoContent(request.getPage(),request.getLimit(),qiniuQueryDto);
    }

    @PostMapping
    @ApiOperation(value = "上传文件")
    @ResponseBody
    @SysLog("上传文件")
    public ResponseBean uploadFile(@RequestParam("file") MultipartFile file) {
        return qiniuContentService.upload(file, qiniuConfigService.getById(1));
    }

    @GetMapping("/config")
    @ApiOperation(value = "打开添加配置页面")
    public String toAddConfig() {
        return "system/qiniu/qiniu-add-config";
    }

    @GetMapping("/getCurrentConfig")
    @ApiOperation(value = "获取已有的配置")
    @ResponseBody
    public ResponseBean getCurrentConfig() {
        return ResponseBean.success("",qiniuConfigService.getById(1));
    }

    @PutMapping("/config")
    @ApiOperation(value = "添加或更新一个七牛云配置")
    @ResponseBody
    @SysLog("添加或更新一个七牛云配置")
    public ResponseBean addConfig(@RequestBody QiniuConfig qiniuConfig) {
        return qiniuConfigService.addConfig(qiniuConfig);
    }

    @GetMapping("/sync")
    @ApiOperation(value = "同步七牛云图床中的图片数据到数据库中")
    @ResponseBody
    @SysLog("同步七牛云图床中的图片数据到数据库中")
    public ResponseBean sync() {
        return qiniuContentService.sync(qiniuConfigService.getById(1));
    }
}
