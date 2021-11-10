package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.pojo.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-10-24
 */
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 分页获取父级留言
     * @param page
     * @return
     */
    IPage<Message> getParentMessages(Page<Message> page);

    /**
     * 获取所有子留言消息
     * @return
     */
    List<Message> getAllMessages();

    /**
     * 后台系统分页获取留言
     * @param page 分页
     * @param wrapper 查询条件
     * @return
     */
    IPage<Message> fuzzyGetMessageList(@Param("page") Page<Message> page, @Param("ew") QueryWrapper<Message> wrapper);

    /**
     * 获取最新的10条留言
     * @return
     */
    List<Message> getNewestMessages();
}
