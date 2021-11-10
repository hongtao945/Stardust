package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.NoticeQueryDto;
import com.lht.admin.pojo.Notice;
import com.lht.admin.mapper.NoticeMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public RespondPageBean fuzzyGetNoticeList(Integer offset, Integer limit, NoticeQueryDto queryDto) {
        Page<Notice> page = new Page<>(offset, limit);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryDto.getContent())) {
            wrapper.like("notice_content", queryDto.getContent());
        }
        if (!StringUtils.isEmpty(queryDto.getStartTime()) && !StringUtils.isEmpty(queryDto.getEndTime())) {
            wrapper.between("create_time", queryDto.getStartTime(), queryDto.getEndTime());
        }
        IPage<Notice> res = noticeMapper.fuzzyGetNoticeList(page, wrapper);
        return new RespondPageBean(res.getTotal(), res.getRecords());
    }

    @Override
    public List<Notice> recentNotice() {
        return noticeMapper.recentNotice();
    }
}
