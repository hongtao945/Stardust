package com.lht.admin.service;

import com.lht.admin.dto.TagQueryDto;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface ITagService extends IService<Tag> {

    /**
     *
     * @param offset
     * @param limit
     * @param query
     * @return
     */
    RespondPageBean fuzzyGetTagList(Integer offset, Integer limit, TagQueryDto query);

    /**
     * 添加一个标签
     * @param tag
     * @return
     */
    ResponseBean addTag(Tag tag);

    /**
     * 获取可选的标签颜色
     * @return
     */
    ResponseBean getColors();

    /**
     * 分页获取所有标签
     * @param curPage
     * @param limit
     * @return
     */
    RespondPageBean getFrontTagList(Integer curPage, Integer limit);

    /**
     * 获取所有标签
     * @return 所有标签
     */
    List<Tag> getAllTags();

    /**
     * 获取总条目数
     * @return 总条目数
     */
    Integer getCount();

    /**
     * 通过id批量删除标签
     * @param tagList
     * @return
     */
    boolean removeTagsById(List<Long> tagList);
}
