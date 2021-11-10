package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.CommentOrMessageQueryDto;
import com.lht.admin.pojo.Message;
import com.lht.admin.mapper.MessageMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-10-24
 */
@Service
@CacheConfig(cacheNames = "Message")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Resource
    private MessageMapper messageMapper;

    @Override
    public RespondPageBean getMessages(Integer offset, Integer limit) {
        Page<Message> page = new Page<>(offset, limit);
        IPage<Message> res = messageMapper.getParentMessages(page);
        List<Message> allMessages = messageMapper.getAllMessages();
        res.getRecords().stream().forEach(message -> {
            message.setChildren(new ArrayList<>());
            buildMessagesTree(message, allMessages, message.getChildren());
        });
        return new RespondPageBean(res.getTotal(), res.getRecords());
    }

    /**
     * 递归构建留言板
     * @param parent 父级留言
     * @param allMessages 所有的留言
     * @param children 子留言
     */
    private void buildMessagesTree(Message parent, List<Message> allMessages, List<Message> children) {
        allMessages.stream().filter(item -> Objects.equals(parent.getMessageId(), item.getFid()))
                .forEach(item -> {
                    children.add(item);
                    buildMessagesTree(item, allMessages, children);
                });
    }

    @Override
    public RespondPageBean fuzzyGetMessageList(Integer offset, Integer limit, CommentOrMessageQueryDto commentOrMessageQueryDto) {
        Page<Message> page = new Page<>(offset, limit);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(commentOrMessageQueryDto.getNickname())) {
            wrapper.like("nickname", commentOrMessageQueryDto.getNickname());
        }
        if (!StringUtils.isEmpty(commentOrMessageQueryDto.getStartTime()) && !StringUtils.isEmpty(commentOrMessageQueryDto.getEndTime())) {
            wrapper.between("create_time", commentOrMessageQueryDto.getStartTime(), commentOrMessageQueryDto.getEndTime());
        }
        wrapper.eq("is_delete", 0);
        IPage<Message> res = messageMapper.fuzzyGetMessageList(page, wrapper);
        return new RespondPageBean(res.getTotal(), res.getRecords());
    }

    @Override
    @Cacheable
    public Integer getCount() {
        return messageMapper.selectCount(null);
    }

    @Override
    public List<Message> getNewestMessages() {
        return messageMapper.getNewestMessages();
    }

}
