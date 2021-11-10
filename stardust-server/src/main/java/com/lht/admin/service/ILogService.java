package com.lht.admin.service;

import com.lht.admin.dto.LogQueryDto;
import com.lht.admin.pojo.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;
import org.aspectj.lang.JoinPoint;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface ILogService extends IService<Log> {

    /**
     * 保存日志信息
     * @param log
     */
    void saveLogAsync(Log log);

    /**
     * 模糊查询日志列表
     * @param page
     * @param limit
     * @param logQueryDto
     * @param type
     * @return
     */
    RespondPageBean fuzzyGetLogList(Integer page, Integer limit, LogQueryDto logQueryDto, String type);

    /**
     * 获取最新的10条错误日志
     * @return 最新的10条错误日志
     */
    List<Log> getNewestErrorLogs();

    /**
     * 保存日志
     * @param log 日志对象
     * @param currentUsername 当前系统的用户名
     * @param browser 所使用的浏览器
     * @param ip ip地址
     * @param joinPoint 切入点
     */
    void save(Log log, String currentUsername, String browser, String ip, JoinPoint joinPoint);

    /**
     * 删除所有Info日志
     * @return
     */
    boolean deleteAllInfoLog();

    /**
     * 删除所有错误日志
     * @return
     */
    boolean deleteAllErrorLog();
}
