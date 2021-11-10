package com.lht.exception.handler;

import com.lht.admin.pojo.ResponseBean;
import com.lht.exception.UserExpirationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lht
 * @date 2021/2/25 - 13:12
 * @description: 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) {
//        log.error("Request URL:{"+request.getRequestURL()+"}");
//        log.error("Exception:{"+e+"}");
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("RequestURL",request.getRequestURL());
//        modelAndView.addObject("Exception",e);
//        modelAndView.setViewName("error/500");
//        return modelAndView;
//    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseBean UsernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        log.error("UsernameNotFoundException===>"+e);
        return ResponseBean.error("用户名不存在");
    }

    @ExceptionHandler(LockedException.class)
    public ResponseBean LockedExceptionHandler(Exception e) {
        log.error("LockedException===>"+e);
        return ResponseBean.error("该账号被封禁，请联系管理员！");
    }

    @ExceptionHandler(UserExpirationException.class)
    public ResponseBean UserExpirationExceptionHandler(UserExpirationException e) {
        log.error("UserExpirationException===>"+e.getMsg());
        return new ResponseBean(e.getCode(), e.getMessage(), null);
    }

}
