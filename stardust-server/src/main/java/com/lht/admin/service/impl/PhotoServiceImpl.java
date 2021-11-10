package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.PhotoQueryDto;
import com.lht.admin.pojo.Photo;
import com.lht.admin.mapper.PhotoMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.utils.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {

    @Resource
    private PhotoMapper photoMapper;

    @Override
    public RespondPageBean fuzzyGetPhotoList(Integer curPage, Integer limit, PhotoQueryDto queryDto) {
        Page<Photo> page = new Page<>(curPage, limit);
        QueryWrapper<Photo> queryWrapper = new QueryWrapper<>();
        if (queryDto.getDescription() != null) {
            queryWrapper.like("description", queryDto.getDescription());
        }
        if(queryDto.getDisplay() != null) {
            queryWrapper.eq("display", queryDto.getDisplay());
        }
        if (queryDto.getStartTime() != null && queryDto.getEndTime() != null) {
            queryWrapper.between("create_time", queryDto.getStartTime(), queryDto.getEndTime());
        }
        IPage<Photo> result = photoMapper.selectPage(page, queryWrapper);
        return new RespondPageBean(result.getTotal(), result.getRecords());
    }
}
