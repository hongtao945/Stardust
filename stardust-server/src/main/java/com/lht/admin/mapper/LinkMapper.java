package com.lht.admin.mapper;

import com.lht.admin.pojo.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
public interface LinkMapper extends BaseMapper<Link> {

    /**
     * 获取友链
     * @return
     */
    List<Link> getFriendLink();
}
