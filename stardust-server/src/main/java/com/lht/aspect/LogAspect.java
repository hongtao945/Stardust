package com.lht.aspect;

import com.lht.admin.pojo.Log;
import com.lht.admin.service.ILogService;
import com.lht.annotation.SysLog;
import com.lht.utils.RequestHolder;
import com.lht.utils.SecurityUtils;
import com.lht.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Author lht
 * @date 2021/4/5 - 14:55
 * @description: 日志切面
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Resource
    private ILogService logService;

    final ThreadLocal<Long> currentTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.lht.annotation.SysLog)")
    public void LogPointCut() {
    }

    /**
     * 配置环绕通知，将日志保存到数据库
     * @param joinPoint
     * @return
     */
    @Around("LogPointCut()")
    public Object saveSysLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        // 定义日志的类型
        Log log = new Log();
        log.setType("INFO");
        log.setOperTime(System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setCreateTime(LocalDateTime.now());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        log.setOperUrl(request.getRequestURI());
        log.setRequestMethod(request.getMethod());
        logService.save(log, SecurityUtils.getCurrentUsername(), StringUtils.getBrowser(request),
                StringUtils.getIp(request), joinPoint);
        return result;
    }

    /**
     * 异常通知配置
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "LogPointCut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        // 定义日志的类型
        Log log = new Log();
        log.setType("ERROR");
        log.setExceptionDetail(StringUtils.getStackTrace(e));
        log.setOperTime(System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setCreateTime(LocalDateTime.now());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        log.setOperUrl(request.getRequestURI());
        log.setRequestMethod(request.getMethod());
        logService.save(log, SecurityUtils.getCurrentUsername(), StringUtils.getBrowser(request),
                StringUtils.getIp(request), joinPoint);
    }

    /**
     * 配置日志信息
     * @param request
     * @param log
     * @param joinPoint
     */
    private void setting(HttpServletRequest request, Log log, ProceedingJoinPoint joinPoint) {
        // 先设置一部分
        log.setBrowser(StringUtils.getBrowser(request));
        log.setIp(StringUtils.getIp(request));
        log.setRequestMethod(request.getMethod());
        log.setOperName(SecurityUtils.getCurrentUsername());
        log.setOperUrl(request.getRequestURI());
        log.setLocation(StringUtils.getCityInfo(log.getIp()));
        // 获取一些信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog aopLog = method.getAnnotation(SysLog.class);
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{ ");
        // 参数值
        Object[] args = joinPoint.getArgs();
        // 参数名称
        String[] argNames = signature.getParameterNames();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].toString().length() > 100) {
                    args[i] = "...";
                }
                params.append(" ").append(argNames[i]).append(": ").append(args[i]);
            }
        }
        // 继续设置
        log.setMethod(methodName);
        log.setTitle(aopLog.value());
        log.setParams(params.toString() +" }");
    }
}
