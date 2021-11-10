package com.lht.admin.mapper;

import com.lht.admin.pojo.Role;
import com.lht.admin.pojo.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 保存角色所对应的菜单树
     * @param roleId
     * @param menuIds
     */
    void save(@Param("roleId") Long roleId, @Param("menuIds") List<Integer> menuIds);

    /**
     * 通过角色id删除其相关的所有菜单树
     * @param roleId 角色id
     */
    void deleteByRoleId(@Param("roleId") Long roleId);

}
