package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.pojo.Classification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface ClassificationMapper extends BaseMapper<Classification> {

    /**
     * 获取所有分类
     * @return 所有分类
     */
    List<Classification> getAllClassification();

    /**
     * 页模糊查询分类列表
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Classification> fuzzyGetClassificationList(@Param("page") Page<Classification> page, @Param("ew") QueryWrapper<Classification> wrapper);

    /**
     * 获取有关联文章的文章分类总数
     * @return 文章分类总数
     */
    Integer getCount();
}
