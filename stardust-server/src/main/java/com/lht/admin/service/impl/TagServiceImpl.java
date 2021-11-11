package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.TagQueryDto;
import com.lht.admin.mapper.TagArticleMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.Tag;
import com.lht.admin.mapper.TagMapper;
import com.lht.admin.pojo.TagArticle;
import com.lht.admin.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.utils.ResultCode;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Service
@CacheConfig(cacheNames = "Tag")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private TagArticleMapper tagArticleMapper;

    @Override
    public RespondPageBean fuzzyGetTagList(Integer offset, Integer limit, TagQueryDto query) {
        Page<Tag> page = new Page<>(offset, limit);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(query.getQueryName())) {
            wrapper.like("t.name", query.getQueryName());
        }
        if (!StringUtils.isEmpty(query.getStartTime()) && !StringUtils.isEmpty(query.getEndTime())) {
            wrapper.between("t.create_time", query.getStartTime(), query.getEndTime());
        }
        IPage<Tag> tags = tagMapper.fuzzyGetTagList(page, wrapper);
        RespondPageBean bean = new RespondPageBean(tags.getTotal(), tags.getRecords());
        return bean;
    }

    @Override
    public ResponseBean addTag(Tag tag) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", tag.getName());
        if (tagMapper.selectOne(queryWrapper) != null) {
            return ResponseBean.error("存在该同名标签！");
        }
        if (tagMapper.insert(tag) > 0) {
            return ResponseBean.success("添加成功!");
        }
        return ResponseBean.error("添加失败！");
    }

    @Override
    public ResponseBean getColors() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("color").groupBy("color");
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        // 将颜色数据取出
        List<String> colors = tags.stream().map(Tag::getColor).collect(Collectors.toList());
        return ResponseBean.success("获取成功!", colors);
    }

    @Override
    public RespondPageBean getFrontTagList(Integer curPage, Integer limit) {
        Page<Tag> page = new Page<>(curPage, limit);
        IPage<Tag> tags = tagMapper.getFrontTagList(page);
        RespondPageBean bean = new RespondPageBean(tags.getTotal(), tags.getRecords());
        return bean;
    }

    @Override
    @Cacheable
    public List<Tag> getAllTags() {
        return tagMapper.getAllTags();
    }

    @Override
    @Cacheable
    public Integer getCount() {
        return tagMapper.getCount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeTagsById(List<Long> tagList) {
        tagList.stream().forEach(id -> tagArticleMapper.delete(new QueryWrapper<TagArticle>().eq("tagId", id)));
        tagMapper.deleteBatchIds(tagList);
        return true;
    }

}
