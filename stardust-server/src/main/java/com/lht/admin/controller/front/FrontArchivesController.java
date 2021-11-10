package com.lht.admin.controller.front;

import com.lht.admin.service.IArticleService;
import com.lht.admin.vo.ArticleDateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lht
 * @date 2021/10/23 - 14:31
 * @description: 前端归档页面控制器
 */
@Api(tags = "前端博客-归档")
@RestController
@RequestMapping("/front/archives")
public class FrontArchivesController {

    @Resource
    private IArticleService articleService;

    @ApiOperation(value = "获取文章创作频率表视图对象")
    @GetMapping
    private List<ArticleDateVo> getArticleDate() {
        return articleService.getArticleByDay();
    }

}
