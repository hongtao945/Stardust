package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lht.admin.dto.ArticleAuditDto;
import com.lht.admin.dto.ArticleQueryDto;
import com.lht.admin.mapper.TagArticleMapper;
import com.lht.admin.mapper.TagMapper;
import com.lht.admin.pojo.*;
import com.lht.admin.mapper.ArticleMapper;
import com.lht.admin.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.admin.service.IElasticSearchService;
import com.lht.admin.service.IRedisService;
import com.lht.admin.vo.ArticleDateVo;
import com.lht.constant.Constant;
import com.lht.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"article"})
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private TagArticleMapper tagArticleMapper;
    @Resource
    private IElasticSearchService elasticSearchService;
    @Resource
    private IRedisService redisService;


    @Override
    @Caching(evict = {
            @CacheEvict(allEntries = true),
    })
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean SaveOrUpdate(Article article) {
        redisService.deleteTagCache();
        article.setWords(wordCount(article.getTextContent()));
        // 看是否有先前未出现的标签，有的话说明是新增的
        List<Tag> newTags = article.getTagList().stream().filter(tag -> tag.getTagId() == null).collect(Collectors.toList());
        for (Tag newTag: newTags) {
            newTag.setColor(Constant.DEFAULT_COLOR);
            newTag.setCreateTime(LocalDateTime.now());
            newTag.setUpdateTime(LocalDateTime.now());
            tagMapper.insert(newTag);
        }
        if (article.getArticleId() == null) {
            //id为空 是新增
            articleMapper.insert(article);
        }else {
            // 否则则是更新
            elasticSearchService.deleteById(article.getArticleId());
            articleMapper.updateById(article);
            // 将原先的标签删除
            tagArticleMapper.delete(new QueryWrapper<TagArticle>().eq("article_id", article.getArticleId()));
        }
        List<Long> tagList = article.getTagList().stream().map(Tag::getTagId).collect(Collectors.toList());
        tagArticleMapper.insertBatch(article.getArticleId(), tagList);
        elasticSearchService.save(article);
        return ResponseBean.success("添加or更新成功!");
    }

    /**
     * 文章字数统计
     * @param string 文章内容
     * @return 文章的字数
     */
    private int wordCount(String string) {
        if (StringUtils.isEmpty(string)) {
            return 0;
        }
        String englishString = string.replaceAll("[\u4e00-\u9fa5]", "");
        String[] englishWords = englishString.split("[\\p{P}\\p{S}\\p{Z}\\s]+");
        int chineseWordCount = string.length() - englishString.length();
        int otherWordCount = englishWords.length;
        if (englishWords.length > 0 && englishWords[0].length() < 1) {
            otherWordCount--;
        }
        if (englishWords.length > 1 && englishWords[englishWords.length - 1].length() < 1) {
            otherWordCount--;
        }
        return chineseWordCount + otherWordCount;
    }

    @Override
    public RespondPageBean fuzzyGetArticles(Integer offset, Integer limit, ArticleQueryDto articleQueryDto) {
        Page<Article> page = new Page<>(offset, limit);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        // 因为前端传过来的数据classId默认为0，所以要多一个判断
        if (articleQueryDto.getClassId() != null && articleQueryDto.getClassId() != 0) {
            wrapper.eq("a.class_id", articleQueryDto.getClassId());
        }
        if (articleQueryDto.getPublished() != null) {
            wrapper.eq("a.published",articleQueryDto.getPublished());
        }
        if (articleQueryDto.getType() != null) {
            wrapper.eq("a.type", articleQueryDto.getType());
        }
        if (articleQueryDto.getRecommend() != null) {
            wrapper.eq("a.recommend", articleQueryDto.getRecommend());
        }
        if (articleQueryDto.getTitle() != null && !"".equals(articleQueryDto.getTitle())) {
            wrapper.like("a.title",articleQueryDto.getTitle());
        }
        if (articleQueryDto.getStatus() != null) {
            wrapper.eq("a.status", articleQueryDto.getStatus());
        }
        if (articleQueryDto.getStartDate() != null && articleQueryDto.getEndDate() != null) {
            wrapper.between("a.create_time", articleQueryDto.getStartDate(), articleQueryDto.getEndDate());
        }
        IPage<Article> articles = articleMapper.fuzzyGetArticles(page, wrapper);
        RespondPageBean articleList = new RespondPageBean(articles.getTotal(), articles.getRecords());
        return articleList;
    }

    @Override
    @Cacheable
    public Article getArticleInfoById(Long articleId) {
        return articleMapper.getArticleInfoById(articleId);
    }

    @Override
    public RespondPageBean getArticlesSortedByDate(Integer offset, Integer limit, Long tagId, Long classId) {
        Page<Article> page = new Page<>(offset, limit);
        IPage<Article> resp = articleMapper.getArticlesSortedByDate(page, tagId, classId);
        return new RespondPageBean(resp.getTotal(), resp.getRecords());
    }

    @Override
    @Cacheable
    public List<Article> getRecommendArticles() {
        return articleMapper.getRecommendArticles();
    }

    @Override
    @Cacheable
    public List<Article> getPrevAndNextArticle(Long id) {
        List<Article> res = new ArrayList<>(2);
        res.add(articleMapper.getPrevArticle(id));
        res.add(articleMapper.getNextArticle(id));
        return res;
    }

    @Override
    @Cacheable
    public List<ArticleDateVo> getArticleByDay() {
        return articleMapper.getArticleByDay();
    }

    @Override
    @Cacheable
    public List<ArticleDateVo> getArticleByMonth() {
        return articleMapper.getArticleByMonth();
    }

    @Override
    @Cacheable
    public Integer getCount() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("published", true);
        wrapper.eq("status", 2);
        return articleMapper.selectCount(wrapper);
    }

    @Override
    @Async
    public void viewsIncrement(Long id) {
        articleMapper.viewsIncrement(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean delete(Long id) {
        elasticSearchService.deleteById(id);
        return articleMapper.deleteById(id) != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean audit(ArticleAuditDto articleAuditDto) {
        elasticSearchService.deleteById(articleAuditDto.getArticleId());
        Article article = new Article();
        article.setArticleId(articleAuditDto.getArticleId());
        article.setStatus(articleAuditDto.getStatus());
        articleMapper.updateById(article);
        article = articleMapper.selectById(articleAuditDto.getArticleId());
        elasticSearchService.save(article);
        return true;
    }
}
