package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.pojo.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通知公告表 Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 模糊查询公告列表
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Notice> fuzzyGetNoticeList(@Param("page") Page<Notice> page, @Param("ew") QueryWrapper<Notice> wrapper);

    /**
     * 获取最近10条公告
     * @return 公告列表
     */
    List<Notice> recentNotice();
}
