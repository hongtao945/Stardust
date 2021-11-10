package com.lht.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lht.admin.pojo.ResponseBean;
import com.lht.utils.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lht
 * @date 2021/4/4 - 20:55
 * @description: 登录成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        String userId = null;
        if (principal != null) {
            userId = principal.getUser().getUserId().toString();
        }
        String parameter = httpServletRequest.getParameter("remember-me");
        boolean rememberMe = Boolean.parseBoolean(parameter);
        // 如果选择了记住我， 就设置cookie
        if (rememberMe) {
            Cookie cookie = new Cookie("userId", userId);
            // 有效值14天
            cookie.setMaxAge(60 * 60 * 24 * 14);
            httpServletResponse.addCookie(cookie);
        }
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.getWriter().write(JsonUtils.toString(ResponseBean.success("登录成功!",objectMapper.writeValueAsString(principal.getUser()))));
    }
}
