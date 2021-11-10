package com.lht.admin.controller;


import com.lht.admin.dto.ClassificationQueryDto;
import com.lht.admin.pojo.Classification;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.IClassificationService;
import com.lht.annotation.SysLog;
import com.lht.utils.BatchRemoveUtils;
import com.lht.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Controller
@RequestMapping("/api/page/classification")
@Api(tags = "后台管理系统-文章分类管理")
public class ClassificationController {

    @Resource
    private IClassificationService classificationService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到分类管理页面")
    public String index() {
        return "system/classification/classification";
    }

    @GetMapping
    @ApiOperation(value = "模糊查询分类列表")
    @ResponseBody
    @SysLog("模糊查询分类列表")
    public RespondPageBean fuzzyGetClassificationList(PageTableRequest request, ClassificationQueryDto queryDto) {
        return classificationService.fuzzyGetClassificationList(request.getPage(), request.getLimit(), queryDto);
    }

    @GetMapping("/colors")
    @ApiOperation(value = "获取可选的标签颜色")
    @ResponseBody
    @SysLog("获取可选的标签颜色")
    public ResponseBean getColors() {
        return classificationService.getColors();
    }

    @GetMapping("/add")
    @ApiOperation(value = "跳转到添加分类页面")
    public String toAddClassification() {
        return "system/classification/classification-add";
    }

    @PostMapping
    @ApiOperation(value = "添加一个分类")
    @ResponseBody
    @SysLog("添加一个分类")
    public ResponseBean addClassification(@RequestBody Classification classification) {
        classification.setCreateTime(LocalDateTime.now());
        classification.setUpdateTime(LocalDateTime.now());
        if (classificationService.save(classification)) {
            return ResponseBean.success("添加成功!");
        }
        return ResponseBean.error("添加失败！");
    }

    @GetMapping("/edit")
    @ApiOperation(value = "跳转到编辑分类页面")
    public String toEditClassification(Model model,Long classId) {
        model.addAttribute("classification",classificationService.getById(classId));
        return "system/classification/classification-edit";
    }

    @PutMapping
    @ApiOperation(value = "编辑更新一个分类")
    @ResponseBody
    @SysLog("编辑更新一个分类")
    public ResponseBean editClassification(@RequestBody Classification classification) {
        classification.setUpdateTime(LocalDateTime.now());
        if (classificationService.updateById(classification)) {
            return ResponseBean.success("更新成功！");
        }
        return ResponseBean.error("更新失败！");
    }

    @DeleteMapping("/{classId}")
    @ApiOperation(value = "删除一个分类")
    @ResponseBody
    @SysLog("删除一个分类")
    public ResponseBean deleteClassification(@PathVariable Long classId) {
        if (classificationService.removeById(classId)) {
            return ResponseBean.success("删除成功！");
        }
        return ResponseBean.error("删除失败！");
    }

    @DeleteMapping("/batchRemove/{ids}")
    @ApiOperation(value = "批量删除分类")
    @ResponseBody
    @SysLog("批量删除分类")
    public ResponseBean batchRemove(@PathVariable("ids") String classIds) {
        List<Long> classList = BatchRemoveUtils.getLongTypeListByStr(classIds);
        if (classificationService.removeByIds(classList)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败！");
    }

    @GetMapping("/allClassification")
    @ApiOperation(value = "获取所有分类")
    @ResponseBody
    @SysLog("获取所有分类")
    public ResponseBean getAllClassification() {
        return ResponseBean.success("",classificationService.list());
    }
}
