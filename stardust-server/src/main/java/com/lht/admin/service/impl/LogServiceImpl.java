package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.LogQueryDto;
import com.lht.admin.pojo.Log;
import com.lht.admin.mapper.LogMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.annotation.SysLog;
import com.lht.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Resource
    private LogMapper logMapper;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLogAsync(Log log) {
        logMapper.insert(log);
    }

    @Override
    public RespondPageBean fuzzyGetLogList(Integer curPage, Integer limit, LogQueryDto logQueryDto, String type) {
        Page<Log> page = new Page<>(curPage, limit);
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);
        if (logQueryDto.getOperator() != null && !"".equals(logQueryDto.getOperator())) {
            queryWrapper.like("oper_name", logQueryDto.getOperator());
        }
        if (logQueryDto.getTitle() != null && !"".equals(logQueryDto.getTitle())) {
            queryWrapper.like("title", logQueryDto.getTitle());
        }
        if (logQueryDto.getStartDate() != null && logQueryDto.getEndDate() != null) {
            queryWrapper.between("create_time", logQueryDto.getStartDate(), logQueryDto.getEndDate());
        }
        IPage<Log> result = logMapper.selectPage(page, queryWrapper);
        return new RespondPageBean(result.getTotal(), result.getRecords());
    }

    @Override
    public List<Log> getNewestErrorLogs() {

        return logMapper.getNewestErrorLogs();
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(Log log, String currentUsername, String browser, String ip, JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        String methodName = joinPoint.getTarget().getClass().getName() + "." + methodSignature.getName() + "()";
        StringBuilder params = new StringBuilder("{");
        // 参数名称
        String[] argNames = methodSignature.getParameterNames();
        // 参数值
        Object[] argValues = joinPoint.getArgs();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                if (argValues[i] != null && argValues[i].toString().length() > 100) {
                    argValues[i] = "...";
                }
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        log.setIp(ip);
        log.setBrowser(browser);
        log.setTitle(sysLog.value());
        log.setMethod(methodName);
        log.setOperName(currentUsername);
        log.setLocation(StringUtils.getCityInfo(ip));
        log.setParams(params.toString() + "}");
        logMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAllInfoLog() {
        return logMapper.delete(new QueryWrapper<Log>().eq("type", "INFO")) != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAllErrorLog() {
        return logMapper.delete(new QueryWrapper<Log>().eq("type", "ERROR")) != 0;
    }
}
