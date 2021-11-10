package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 管理系统-后台用户表 Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 模糊查询用户列表
     * @param page 分页
     * @param wrapper 查询参数封装
     * @return 用户列表
     */
    IPage<User> fuzzyGetUserList(@Param("page") Page<User> page, @Param("ew") QueryWrapper<User> wrapper);
}
