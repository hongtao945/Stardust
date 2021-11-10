package com.lht.admin.service;

import com.lht.admin.dto.ClassificationQueryDto;
import com.lht.admin.pojo.Classification;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface IClassificationService extends IService<Classification> {

    /**
     * 获取所有可选的分类颜色
     * @return
     */
    ResponseBean getColors();

    /**
     * 获取所有分类
     * @return 所有分类
     */
    List<Classification> getAllClassification();

    /**
     * 获取总条目数
     * @return 总条目数
     */
    Integer getCount();

    /**
     * 分页模糊查询分类列表
     * @param offset
     * @param limit
     * @param queryDto
     * @return
     */
    RespondPageBean fuzzyGetClassificationList(Integer offset, Integer limit, ClassificationQueryDto queryDto);
}
