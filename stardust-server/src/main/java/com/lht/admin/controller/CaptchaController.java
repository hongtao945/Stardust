package com.lht.admin.controller;

import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author lht
 * @date 2021/4/4 - 20:25
 * @description: 前端验证码控制器
 */
@Controller
@Api(tags = "后台管理系统-验证码")
public class CaptchaController {
    @RequestMapping(value = "/captcha", produces = "image/jpeg")
    @ApiOperation(value = "验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(120, 45, 4, request, response);
    }
}
