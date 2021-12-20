package com.lht.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lht.admin.dto.ArticleAuditDto;
import com.lht.admin.dto.ArticleQueryDto;
import com.lht.admin.pojo.*;
import com.lht.admin.service.IArticleService;
import com.lht.admin.service.ITagArticleService;
import com.lht.annotation.SysLog;
import com.lht.constant.Constant;
import com.lht.utils.JsonUtils;
import com.lht.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Controller
@RequestMapping("/api/page/article")
@Api(tags = "后台管理系统-文章管理")
public class ArticleController {

    @Resource
    private IArticleService articleService;
    @Resource
    private ITagArticleService tagArticleService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到文章管理页面")
    @SysLog("跳转到文章管理页面")
    public String index() {
        return "system/article/article";
    }

    @GetMapping
    @ApiOperation(value = "分页模糊查询文章列表")
    @ResponseBody
    @SysLog("分页模糊查询文章列表")
    public RespondPageBean fuzzyGetArticles(PageTableRequest request, ArticleQueryDto articleQueryDto) {
        return articleService.fuzzyGetArticles(request.getPage(), request.getLimit(), articleQueryDto);
    }

    @GetMapping("/article-add")
    @ApiOperation(value = "跳转到发布文章页面")
    @SysLog("跳转到发布文章页面")
    public String toAddArticle() {
        return "system/article/article-addOrEdit";
    }

    @GetMapping("/article-edit")
    @ApiOperation(value = "跳转到编辑文章页面")
    @SysLog("跳转到编辑文章页面")
    public String toEditArticle(Model model, Long articleId) {
        model.addAttribute("article", articleService.getArticleById(articleId));
        return "system/article/article-addOrEdit";
    }

    @PostMapping
    @ApiOperation(value = "添加一篇文章")
    @ResponseBody
    @SysLog("添加一篇文章")
    public ResponseBean save(@RequestBody Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        article.setViews(0);
        article.setWords(0);
        article.setComments(0);
        article.setRecommend(article.getRecommend() != null);
        article.setAuthorId(SecurityUtils.getCurrentUser().getUserId());
        article.setStatus(Article.StatusEnum.WAIT.getCode());
        return articleService.SaveOrUpdate(article);
    }

    @PutMapping
    @ApiOperation(value = "更新一篇文章")
    @ResponseBody
    @SysLog("更新一篇文章")
    public ResponseBean update(@RequestBody Article article) {
        article.setRecommend(article.getRecommend() != null);
        article.setStatus(Article.StatusEnum.WAIT.getCode());
        article.setUpdateTime(LocalDateTime.now());
        return articleService.SaveOrUpdate(article);
    }

    @GetMapping("/toAudit")
    @ApiOperation(value = "打开审核文章页面")
    public String toAudit(Model model, Long articleId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("article_id", "status");
        wrapper.eq("article_id", articleId);
        Article article = articleService.getOne(wrapper);
        model.addAttribute("article", article);
        return "system/article/article-audit";
    }

    @PutMapping("/audit")
    @ApiOperation(value = "审核文章")
    @ResponseBody
    @SysLog("审核文章")
    public ResponseBean audit(@RequestBody ArticleAuditDto articleAuditDto) {
        if (articleService.audit(articleAuditDto)) {
            return ResponseBean.success("操作成功!");
        }
        return ResponseBean.error("操作失败!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除一篇文章")
    @ResponseBody
    @SysLog("删除一篇文章")
    public ResponseBean delete(@PathVariable Long id) {
        if (tagArticleService.remove(new QueryWrapper<TagArticle>().eq("article_id", id)) && articleService.delete(id)) {
            return ResponseBean.success("删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }
}
