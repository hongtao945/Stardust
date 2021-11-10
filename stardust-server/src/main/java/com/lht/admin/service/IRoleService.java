package com.lht.admin.service;

import com.lht.admin.dto.RoleDto;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface IRoleService extends IService<Role> {

    /**
     * 模糊查询角色列表
     * @param offset 起始页
     * @param limit 一页有多少条数据
     * @param roleName  通过角色名查询
     * @return
     */
    RespondPageBean fuzzyGetRolesByPage(Integer offset, Integer limit, String roleName);

    /**
     * 添加一个角色
     * @param role
     * @return
     */
    ResponseBean addRole(RoleDto role);

    /**
     * 更新一个角色信息
     * @param role
     * @return
     */
    ResponseBean updateRole(RoleDto role);

    /**
     * 通过id删除角色
     * @param roleId 角色id
     * @return
     */
    ResponseBean deleteRoleByRoleId(Long roleId);

    /**
     * 通过用户的id查询对应的角色
     * @param userId
     * @return
     */
    List<Role> getRoleListByUserId(Long userId);
}
