package com.lht.security;

import com.lht.admin.pojo.ResponseBean;
import com.lht.utils.JsonUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lht
 * @date 2021/4/4 - 21:22
 * @description: 授权失败处理器
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        if (e instanceof BadCredentialsException) {
            httpServletResponse.getWriter().write(JsonUtils.toString(ResponseBean.error("用户名或密码错误！")));
        } else if (e instanceof DisabledException) {
            httpServletResponse.getWriter().write(JsonUtils.toString(ResponseBean.error("用户被锁定,请联系管理员解锁！")));
        } else if (e instanceof LockedException) {
            httpServletResponse.getWriter().write(JsonUtils.toString(ResponseBean.error("用户已被锁定，请联系管理员！")));
        } else  {
            httpServletResponse.getWriter().write(JsonUtils.toString(ResponseBean.error("此用户不存在！")));
        }
    }
}
