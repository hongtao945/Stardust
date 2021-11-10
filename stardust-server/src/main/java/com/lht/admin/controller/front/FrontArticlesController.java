package com.lht.admin.controller.front;

import com.lht.admin.pojo.Article;
import com.lht.admin.pojo.EsArticleModel;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.service.IArticleService;
import com.lht.admin.service.IElasticSearchService;
import com.lht.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lht
 * @date 2021/4/21 - 0:11
 * @description: 前端博客文章控制器
 */
@RestController
@RequestMapping("/front/article")
@Api(tags = "前端博客-文章")
public class FrontArticlesController {

    @Resource
    private IArticleService articleService;

    @Resource
    private IElasticSearchService elasticSearchService;

    @GetMapping
    @ApiOperation(value = "根据创建的日期排序分页获取文章")
    public RespondPageBean getArticlesSortedByDate(PageTableRequest request,
                                                   @RequestParam("tagId") Long tagId,
                                                   @RequestParam("classId") Long classId) {
        return articleService.getArticlesSortedByDate(request.getPage(), request.getLimit(), tagId, classId);
    }

    @GetMapping("/recommend")
    @ApiOperation(value = "获取推荐文章")
    public List<Article> getRecommendArticles() {
        return articleService.getRecommendArticles();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取文章")
    public Article getArticleById(@PathVariable("id") Long id) {
        return articleService.getArticleInfoById(id);
    }

    @GetMapping("/{id}/pn")
    @ApiOperation(value = "获取一篇文章的上一篇和下一篇文章")
    public List<Article> getPrevAndNextArticle(@PathVariable("id") Long id) {
        return articleService.getPrevAndNextArticle(id);
    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索文章")
    public List<EsArticleModel> searchArticle(@RequestParam("keyword") String keyword) {
        return elasticSearchService.search(keyword);
    }

    @GetMapping("/{id}/views")
    @ApiOperation(value = "浏览量增加一")
    public void viewsIncrement(@PathVariable("id") Long id) {
        articleService.viewsIncrement(id);
    }

}
