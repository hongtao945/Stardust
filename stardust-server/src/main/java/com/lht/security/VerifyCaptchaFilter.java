package com.lht.security;

import cn.hutool.core.util.StrUtil;
import com.lht.admin.pojo.ResponseBean;
import com.lht.utils.JsonUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author lht
 * @date 2021/4/4 - 20:33
 * @description: 验证码校验过滤器
 */
@Component
public class VerifyCaptchaFilter extends OncePerRequestFilter {

    private String processUrl = "/login";
    private String method = "post";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (method.equalsIgnoreCase(httpServletRequest.getMethod()) && processUrl.equals(httpServletRequest.getServletPath())) {
            // 将session里面的验证码取出
            HttpSession session = httpServletRequest.getSession();
            String captcha = (String) session.getAttribute("captcha");
            // 完事后将session里面的验证码信息清除
            session.removeAttribute("captcha");
            // 获取用户输入的验证码
            String userInputCaptcha = httpServletRequest.getParameter("captcha");

            httpServletResponse.setContentType("application/json;charset=UTF-8");
            // 下面进行校验
            if (StrUtil.isEmpty(userInputCaptcha)) {
                httpServletResponse.getWriter().write(JsonUtils.toString(ResponseBean.error("验证码不可为空！")));
                return;
            }
            if (StrUtil.isEmpty(captcha)) {
                httpServletResponse.getWriter().write(JsonUtils.toString(ResponseBean.error("验证码已经失效啦！")));
                return;
            }
            if (!userInputCaptcha.equalsIgnoreCase(captcha)) {
                httpServletResponse.getWriter().write(JsonUtils.toString(ResponseBean.error("验证码输入错误！")));
                return;
            }
        }
        // 没问题就放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
