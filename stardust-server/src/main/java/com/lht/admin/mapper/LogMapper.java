package com.lht.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.pojo.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface LogMapper extends BaseMapper<Log> {

    /**
     * 获取最新的10条错误日志
     * @return 最新的10条错误日志
     */
    List<Log> getNewestErrorLogs();
}
