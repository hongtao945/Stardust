package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.UserAddDto;
import com.lht.admin.dto.UserQueryDto;
import com.lht.admin.mapper.UserRoleMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.User;
import com.lht.admin.mapper.UserMapper;
import com.lht.admin.pojo.UserRole;
import com.lht.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.security.LoginUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理系统-后台用户表 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Value("${user.base-avatar}")
    private String avatarUrl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean resetPassword(String oldPassword, String newPassword) {
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 密文密码
        String password = user.getPassword();
        if (!passwordEncoder.matches(oldPassword, password)) {
            return ResponseBean.error("旧密码不匹配!");
        }
        User user1 = user.getUser();
        user1.setPassword(passwordEncoder.encode(newPassword));
        if (userMapper.updateById(user1) != 0) {
            return ResponseBean.success("修改成功!");
        }
        return ResponseBean.error("未知错误!");
    }

    @Override
    public RespondPageBean fuzzyGetUserList(Integer offset, Integer limit, UserQueryDto queryDto) {
        Page<User> page = new Page<>(offset, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryDto.getUsername())) {
            wrapper.like("username", queryDto.getUsername());
        }
        if (!StringUtils.isEmpty(queryDto.getNickname())) {
            wrapper.like("nickname", queryDto.getNickname());
        }
        if (!StringUtils.isEmpty(queryDto.getEmail())) {
            wrapper.like("email", queryDto.getEmail());
        }
        IPage<User> res = userMapper.fuzzyGetUserList(page, wrapper);
        return new RespondPageBean(res.getTotal(), res.getRecords());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(UserAddDto userAddDto) {
        User user = new User();
        user.setCreateTime(LocalDateTime.now());
        user.setUsername(userAddDto.getUsername());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setStatus(true);
        user.setAvatar(avatarUrl);
        userMapper.insert(user);
        userAddDto.getRoleIds().stream().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long id) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        if (userMapper.deleteById(id) != 0 && userRoleMapper.delete(wrapper) != 0) {
            return true;
        }
        return false;
    }
}
