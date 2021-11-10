package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.LinkAuditDto;
import com.lht.admin.dto.LinkQueryDto;
import com.lht.admin.pojo.Link;
import com.lht.admin.mapper.LinkMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.ILinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.utils.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {

    @Resource
    private LinkMapper linkMapper;

    @Override
    public RespondPageBean fuzzyGetLinkList(Integer curPage, Integer limit, LinkQueryDto queryDto) {
        Page<Link> page = new Page<>(curPage, limit);
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        if (queryDto.getNickName() != null) {
            queryWrapper.like("nick_name", queryDto.getNickName());
        }
        if (queryDto.getStatus() != null) {
            queryWrapper.eq("status", queryDto.getStatus());
        }
        if (queryDto.getStartTime() != null && queryDto.getEndTime() != null) {
            queryWrapper.between("create_time", queryDto.getStartTime(), queryDto.getEndTime());
        }
        IPage<Link> result = linkMapper.selectPage(page, queryWrapper);
        return new RespondPageBean(result.getTotal(), result.getRecords());
    }

    @Override
    public ResponseBean audit(LinkAuditDto auditDto) {
        Link link = new Link();
        link.setLinkId(auditDto.getLinkId());
        link.setStatus(auditDto.getStatus());
        linkMapper.updateById(link);
        return ResponseBean.success("审核成功!");
    }

    @Override
    public List<Link> getFriendLink() {
        return linkMapper.getFriendLink();
    }
}
