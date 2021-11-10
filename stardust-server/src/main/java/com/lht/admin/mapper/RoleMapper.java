package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.RoleDto;
import com.lht.admin.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 模糊查询角色
     *
     * @param page
     * @param roleName
     * @return
     */
    IPage<Role> fuzzyGetRolesByPage(Page<Role> page, @Param("roleName") String roleName);

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    int addRole(@Param("role") RoleDto role);

    /**
     * 通过用户的id查询对应的角色
     *
     * @param userId
     * @return
     */
    List<Role> getRoleListByUserId(@Param("userId") Long userId);
}
