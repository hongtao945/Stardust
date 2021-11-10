package com.lht.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lht.admin.dto.MenuIndexDto;
import com.lht.admin.pojo.Role;
import com.lht.admin.pojo.User;
import com.lht.admin.service.IMenuService;
import com.lht.admin.service.IRoleService;
import com.lht.admin.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author lht
 * @date 2021/4/4 - 19:25
 * @description: UserDetailsService实现类
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private IUserService userService;
    @Resource
    private IMenuService menuService;
    @Resource
    private IRoleService roleService;

    @Override
    public LoginUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User tempUser = userService.getOne(new QueryWrapper<User>().eq("username", username));
        // 数据库查找不到该用户
        if (tempUser == null) {
            throw new BadCredentialsException("用户名不存在");
        }
        // 用户的账号被封禁
//        if (!tempUser.getStatus()) {
//            throw new DisabledException("用户被锁定,请联系管理员解锁");
//        }
        tempUser.setLastLoginTime(LocalDateTime.now());
        // 通过了上面的检测，则说明用户是没问题的，开始为这个用户授权
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 通过用户查询其对应的菜单
        List<MenuIndexDto> menuList = menuService.getMenuBsyUserId(tempUser.getUserId());
        // 将查询到的权限取出放到一个列表中
        List<String> collect = menuList.stream().map(MenuIndexDto::getPermission).collect(Collectors.toList());
        // 遍历这个列表
        for (String permission : collect) {
            if (StringUtils.isEmpty(permission)) {
                continue;
            }
            // 判断没问题后就放到用户的权限列表中
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        // 查询用户所拥有的角色
        List<Role> roleList = roleService.getRoleListByUserId(tempUser.getUserId());
        // 将所得的权限赋予给用户
        LoginUser user = new LoginUser(tempUser, roleList, authorities);
        return user;
    }


}
