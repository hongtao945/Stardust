package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.RoleDto;
import com.lht.admin.mapper.RoleMenuMapper;
import com.lht.admin.mapper.UserRoleMapper;
import com.lht.admin.pojo.*;
import com.lht.admin.mapper.RoleMapper;
import com.lht.admin.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.utils.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public RespondPageBean fuzzyGetRolesByPage(Integer offset, Integer limit, String roleName) {
        Page<Role> page = new Page<>(offset, limit);
        IPage<Role> roles = roleMapper.fuzzyGetRolesByPage(page, roleName);
        RespondPageBean roleList = new RespondPageBean(0, roles.getTotal(), roles.getRecords());
        return roleList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean addRole(RoleDto role) {
        role.setDataScope("1");
        roleMapper.addRole(role);
        List<Integer> menuIds = role.getMenuIds();
        //移除0,permission id是从1开始
        //2、保存角色对应的所有权限
        if (!CollectionUtils.isEmpty(menuIds)) {
            roleMenuMapper.save(role.getRoleId(), menuIds);
        }
        return ResponseBean.success("添加成功!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean updateRole(RoleDto role) {
        List<Integer> menuIds = role.getMenuIds();
        // menuIds.remove(0L);
        // 更新角色的权限之前要先删除他的所有权限
        roleMenuMapper.deleteByRoleId(role.getRoleId());
        // 如果menuIds不为空的话，说明前台选择为这个角色赋予权限
        if (!CollectionUtils.isEmpty(menuIds)) {
            roleMenuMapper.save(role.getRoleId(), menuIds);
        }
        // 更新角色表
        if (roleMapper.updateById(role) > 0) {
            return ResponseBean.success("更新成功！");
        }
        return ResponseBean.error("更新失败！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean deleteRoleByRoleId(Long roleId) {
        if (userRoleMapper.selectCount(new QueryWrapper<UserRole>().eq("role_id", roleId)) > 0) {
            return ResponseBean.error("该角色关联了其他数据，无法删除！");
        }else {
            roleMapper.delete(new QueryWrapper<Role>().eq("role_id", roleId));
            roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
            return ResponseBean.success("删除成功！");
        }
    }

    @Override
    public List<Role> getRoleListByUserId(Long userId) {
        return roleMapper.getRoleListByUserId(userId);
    }
}
