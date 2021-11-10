package com.lht.security;

import com.lht.admin.pojo.ResponseBean;
import com.lht.utils.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lht
 * @date 2021/4/4 - 20:53
 * @description: 当未登录或者token失效访问接口时，自定义的返回结果
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().println(JsonUtils.toString(ResponseBean.error("尚未登录，或者登录过期!")));
        httpServletResponse.getWriter().flush();
    }
}
