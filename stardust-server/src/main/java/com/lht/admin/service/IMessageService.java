package com.lht.admin.service;

import com.lht.admin.dto.CommentOrMessageQueryDto;
import com.lht.admin.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lht
 * @since 2021-10-24
 */
public interface IMessageService extends IService<Message> {

    /**
     * 前端博客页面分页获取留言
     * @param offset
     * @param limit
     * @return
     */
    RespondPageBean getMessages(Integer offset, Integer limit);

    /**
     * 后台系统分页获取留言
     * @param offset
     * @param limit
     * @param commentOrMessageQueryDto 查询参数
     * @return
     */
    RespondPageBean fuzzyGetMessageList(Integer offset, Integer limit, CommentOrMessageQueryDto commentOrMessageQueryDto);

    /**
     * 获取留言总数
     * @return
     */
    Integer getCount();

    /**
     * 获取最新的10条留言
     * @return
     */
    List<Message> getNewestMessages();
}
