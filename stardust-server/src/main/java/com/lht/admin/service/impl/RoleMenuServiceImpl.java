package com.lht.admin.service.impl;

import com.lht.admin.pojo.RoleMenu;
import com.lht.admin.mapper.RoleMenuMapper;
import com.lht.admin.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
