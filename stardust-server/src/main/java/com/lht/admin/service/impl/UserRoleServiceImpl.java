package com.lht.admin.service.impl;

import com.lht.admin.pojo.UserRole;
import com.lht.admin.mapper.UserRoleMapper;
import com.lht.admin.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
