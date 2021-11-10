package com.lht.admin.service;

import com.lht.admin.dto.LinkAuditDto;
import com.lht.admin.dto.LinkQueryDto;
import com.lht.admin.pojo.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface ILinkService extends IService<Link> {

    /**
     * 模糊查询友链列表
     * @param page
     * @param limit
     * @param queryDto
     * @return
     */
    RespondPageBean fuzzyGetLinkList(Integer page, Integer limit, LinkQueryDto queryDto);

    /**
     * 对友链进行审核
     * @param auditDto
     * @return
     */
    ResponseBean audit(LinkAuditDto auditDto);

    /**
     * 获取友链
     * @return
     */
    List<Link> getFriendLink();

}
