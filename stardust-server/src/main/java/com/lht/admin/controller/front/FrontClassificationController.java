package com.lht.admin.controller.front;

import com.lht.admin.pojo.Classification;
import com.lht.admin.service.IClassificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lht
 * @date 2021/10/23 - 19:37
 * @description: 前台博客页面分类控制器
 */
@Api(tags = "前端博客-文章分类")
@RestController
@RequestMapping("/front/classification")
public class FrontClassificationController {

    @Resource
    private IClassificationService classificationService;

    @GetMapping
    @ApiOperation(value = "获取所有分类")
    public List<Classification> getAllClassification() {
        return classificationService.getAllClassification();
    }

}
