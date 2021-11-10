package com.lht.admin.controller.front;

import com.lht.admin.pojo.Tag;
import com.lht.admin.service.IArticleService;
import com.lht.admin.service.IClassificationService;
import com.lht.admin.service.ITagService;
import com.lht.admin.vo.AboutVo;
import com.lht.annotation.SysLog;
import com.lht.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * @Author lht
 * @date 2021/10/24 - 2:01
 * @description: 前端关于我页面控制器
 */
@RestController
@Api(tags = "前端博客-关于我")
@RequestMapping("/front/about")
@Slf4j
public class FrontAboutController {

    @Resource
    private ITagService tagService;
    @Resource
    private IClassificationService classificationService;
    @Resource
    private IArticleService articleService;
    @Resource
    private Executor executor;

    @GetMapping
    @ApiOperation(value = "获取文章、分类、标签总数目")
    public AboutVo getCount() throws ExecutionException, InterruptedException {
        AboutVo aboutVo = new AboutVo();
        CompletableFuture<Void>[] tasks = new CompletableFuture[6];
        tasks[0] = CompletableFuture.runAsync(() -> aboutVo.setArticleCount(articleService.getCount()), executor);
        tasks[1] = CompletableFuture.runAsync(() -> aboutVo.setClassificationCount(classificationService.getCount()), executor);
        tasks[2] = CompletableFuture.runAsync(() -> aboutVo.setTagCount(tagService.getCount()), executor);
        tasks[3] = CompletableFuture.runAsync(() -> aboutVo.setArticleDateVoList(articleService.getArticleByMonth()), executor);
        tasks[4] = CompletableFuture.runAsync(() -> {
            List<Tag> tags = tagService.getAllTags();
            tags.sort((a, b) -> b.getArticleCount() - a.getArticleCount());
            aboutVo.setTags(tags);
        }, executor);
        tasks[5] = CompletableFuture.runAsync(() -> aboutVo.setClassifications(classificationService.getAllClassification()), executor);
        CompletableFuture.allOf(tasks).get();
        return aboutVo;
    }

}
