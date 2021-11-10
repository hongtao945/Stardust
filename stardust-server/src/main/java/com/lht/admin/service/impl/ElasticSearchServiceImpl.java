package com.lht.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.lht.admin.mapper.ArticleMapper;
import com.lht.admin.pojo.Article;
import com.lht.admin.pojo.EsArticleModel;
import com.lht.admin.service.IElasticSearchService;
import com.lht.config.MyElasticSearchConfiguration;
import com.lht.constant.EsConstant;
import com.lht.utils.JsonUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lht
 * @date 2021/10/25 - 19:59
 * @description: es检索服务实现类
 */
@Service
@Slf4j
public class ElasticSearchServiceImpl implements IElasticSearchService {

    @Resource
    private RestHighLevelClient client;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public List<EsArticleModel> search(String keyword) {
        List<EsArticleModel> res = null;
        SearchRequest request = buildSearchRequest(keyword);
        try {
            SearchResponse response = client.search(request, MyElasticSearchConfiguration.COMMON_OPTIONS);
            res = buildResultFromResponse(response);
        } catch (IOException e) {
            log.error("es查询异常=========> {}", e);
        }
        return res;
    }

    /**
     * 构建检索请求
     * @param keyword 关键字
     * @return 检索请求
     */
    private SearchRequest buildSearchRequest(String keyword) {
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 开始构建DSL语句
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 必须是已发布的,不参与计算得分 filter
        TermQueryBuilder published = QueryBuilders.termQuery(EsConstant.PUBLISHED, Article.PublishEnum.YES.getFlag());
        // 必须是审核通过的,不参与计算得分 filter
        TermQueryBuilder mustPass = QueryBuilders.termQuery(EsConstant.STATUS, Article.StatusEnum.PASS.getCode());
        // 关键字必须匹配, 参与计算得分 must
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, EsConstant.TITLE, EsConstant.SUMMARY, EsConstant.HTML_CONTENT);
        boolQueryBuilder.filter(published).filter(mustPass).must(multiMatchQueryBuilder);
        builder.query(boolQueryBuilder);

        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(EsConstant.TITLE);
        highlightBuilder.field(EsConstant.SUMMARY);
        highlightBuilder.field(EsConstant.HTML_CONTENT);
        highlightBuilder.preTags(EsConstant.HIGHLIGHT_PRE_TAGS);
        highlightBuilder.postTags(EsConstant.HIGHLIGHT_POST_TAGS);
        builder.highlighter(highlightBuilder);

        SearchRequest request = new SearchRequest(new String[]{EsConstant.INDEX_NAME}, builder);
        return request;
    }

    /**
     * 从返回回来的Json数据中构建目标数据
     * @param response
     * @return
     */
    private List<EsArticleModel> buildResultFromResponse(SearchResponse response) {
        if (response.getHits() == null || response.getHits().getHits() == null || response.getHits().getHits().length == 0) {
            return new ArrayList<>();
        }

        SearchHit[] hits = response.getHits().getHits();

        List<EsArticleModel> res = Arrays.stream(hits).map(hit -> {
            EsArticleModel articleModel = new EsArticleModel();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            highlight(sourceAsMap, hit.getHighlightFields().get(EsConstant.HTML_CONTENT), EsConstant.HTML_CONTENT);
            highlight(sourceAsMap, hit.getHighlightFields().get(EsConstant.TITLE), EsConstant.TITLE);
            highlight(sourceAsMap, hit.getHighlightFields().get(EsConstant.SUMMARY), EsConstant.SUMMARY);
            articleModel.setArticleId((Long.valueOf((Integer) sourceAsMap.get(EsConstant.ARTICLE_ID))));
            articleModel.setTitle((String) sourceAsMap.get(EsConstant.TITLE));
            articleModel.setSummary((String) sourceAsMap.get(EsConstant.SUMMARY));
            articleModel.setHtmlContent((String) sourceAsMap.get(EsConstant.HTML_CONTENT));
            articleModel.setPublished(true);
            articleModel.setStatus(2);
            return articleModel;
        }).collect(Collectors.toList());
        return res;
    }

    private void highlight(Map<String, Object> sourceAsMap, HighlightField highlightField, String field) {
        if (highlightField == null) {
            return;
        }
        Text[] fragments = highlightField.getFragments();
        StringBuilder sb = new StringBuilder();
        for (Text fragment : fragments) {
            sb.append(fragment);
        }
        sourceAsMap.put(field, sb.toString());
    }

    @Override
    public void save(Article article) {
        EsArticleModel articleModel = new EsArticleModel();
        BeanUtils.copyProperties(article, articleModel);
        // 默认是在审核状态
        if (articleModel.getStatus() == null) {
            articleModel.setStatus(Article.StatusEnum.WAIT.getCode());
        }
        // 默认是不发布
        if (articleModel.getPublished() == null) {
            articleModel.setPublished(Article.PublishEnum.NO.getFlag());
        }
        IndexRequest request = new IndexRequest(EsConstant.INDEX_NAME);
        request.id(String.valueOf(article.getArticleId()));
        String jsonString = JSONUtil.toJsonStr(articleModel);
        request.source(jsonString, XContentType.JSON);
        try {
            client.index(request, MyElasticSearchConfiguration.COMMON_OPTIONS);
        } catch (IOException e) {
            log.error("es添加异常=========> {}", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        DeleteRequest request = new DeleteRequest(EsConstant.INDEX_NAME, String.valueOf(id));
        try {
            client.delete(request, MyElasticSearchConfiguration.COMMON_OPTIONS);
        } catch (IOException e) {
            log.error("es删除异常=========> {}", e);
        }
    }

    @Override
    public void deleteAll() {
        List<Article> articles = articleMapper.selectList(null);
        BulkRequest request = new BulkRequest(EsConstant.INDEX_NAME);
        articles.forEach(article -> {
            request.add(new DeleteRequest(EsConstant.INDEX_NAME, String.valueOf(article.getArticleId())));
        });
        try {
            client.bulk(request, MyElasticSearchConfiguration.COMMON_OPTIONS);
        } catch (IOException e) {
            log.error("es批量删除异常=========> {}", e);
        }
    }

    @Override
    public void sync() {
        this.deleteAll();
        List<Article> articles = articleMapper.selectList(null);
        articles.forEach(article -> this.save(article));
    }
}
