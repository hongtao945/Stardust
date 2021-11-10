package com.lht.admin.service;

import com.lht.admin.dto.PhotoQueryDto;
import com.lht.admin.pojo.Photo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface IPhotoService extends IService<Photo> {

    /**
     * 模糊查询图片列表
     * @param page
     * @param limit
     * @param queryDto
     * @return
     */
    RespondPageBean fuzzyGetPhotoList(Integer page, Integer limit, PhotoQueryDto queryDto);
}
