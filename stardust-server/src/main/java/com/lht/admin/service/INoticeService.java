package com.lht.admin.service;

import com.lht.admin.dto.NoticeQueryDto;
import com.lht.admin.pojo.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;

import java.util.List;

/**
 * <p>
 * 通知公告表 服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface INoticeService extends IService<Notice> {

    /**
     * 模糊查询公告列表
     * @param offset
     * @param limit
     * @param queryDto
     * @return 公告列表
     */
    RespondPageBean fuzzyGetNoticeList(Integer offset, Integer limit, NoticeQueryDto queryDto);

    /**
     * 获取最近8条公告
     * @return 公告列表
     */
    List<Notice> recentNotice();
}
