package com.lht.utils;

import com.lht.admin.pojo.User;
import com.lht.exception.UserExpirationException;
import com.lht.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetails;


/**
 * @Author lht
 * @date 2021/4/5 - 15:49
 * @description: 系统用户工具类
 */
public class SecurityUtils {


    public static String getCurrentUserIp() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserExpirationException();
        }
        Object details = authentication.getDetails();
        if (! (details instanceof WebAuthenticationDetails)) {
            return "";
        }
        return ((WebAuthenticationDetails) details).getRemoteAddress();
    }

    /**
     * 获取系统当前登录的用户的用户名
     * @return
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "前台游客";
        }
        return ((LoginUser) authentication.getPrincipal()).getUsername();
    }

    public static User getCurrentUser() {
        return ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }


}
