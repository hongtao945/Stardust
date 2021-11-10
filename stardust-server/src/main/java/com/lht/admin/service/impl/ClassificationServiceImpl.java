package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.ClassificationQueryDto;
import com.lht.admin.pojo.Classification;
import com.lht.admin.mapper.ClassificationMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.Tag;
import com.lht.admin.service.IClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Service
@CacheConfig(cacheNames = "Classification")
public class ClassificationServiceImpl extends ServiceImpl<ClassificationMapper, Classification> implements IClassificationService {

    @Resource
    private ClassificationMapper classificationMapper;

    @Override
    public ResponseBean getColors() {
        List<Classification> classificationList = classificationMapper.selectList(new QueryWrapper<Classification>().select("color").groupBy("color"));
        List<String> colors = classificationList.stream().map(Classification::getColor).collect(Collectors.toList());
        return ResponseBean.success("",colors);
    }

    @Override
    @Cacheable
    public List<Classification> getAllClassification() {
        return classificationMapper.getAllClassification();
    }

    @Override
    @Cacheable
    public Integer getCount() {
        return classificationMapper.getCount();
    }

    @Override
    public RespondPageBean fuzzyGetClassificationList(Integer offset, Integer limit, ClassificationQueryDto queryDto) {
        Page<Classification> page = new Page<>(offset, limit);
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryDto.getName())) {
            wrapper.like("c.name", queryDto.getName());
        }
        if (!StringUtils.isEmpty(queryDto.getStartTime()) && !StringUtils.isEmpty(queryDto.getEndTime())) {
            wrapper.between("c.create_time", queryDto.getStartTime(), queryDto.getEndTime());
        }
        if (queryDto.getDisplay() != null) {
            wrapper.eq("c.display", queryDto.getDisplay());
        }
        IPage<Classification> res = classificationMapper.fuzzyGetClassificationList(page, wrapper);
        return new RespondPageBean(res.getTotal(), res.getRecords());
    }
}
