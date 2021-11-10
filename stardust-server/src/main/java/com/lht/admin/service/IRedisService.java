package com.lht.admin.service;

import com.lht.admin.vo.PvAndUvVo;
import com.lht.admin.vo.ViewsDateVo;

import java.util.List;

/**
 * @Author lht
 * @date 2021/10/30 - 18:53
 * @description: redis服务接口
 */
public interface IRedisService {
    /**
     * 获取前台的PV和UV
     * @return PV和UV
     */
    PvAndUvVo getPvAndUv();

    /**
     * 增加前台的PV和UV
     * @param ip ip地址
     */
    void increment(String ip);

    /**
     * 近半个月的访问量增减情况
     * @return
     */
    ViewsDateVo halfMonthViewInfo();

    /**
     * 删除关于标签的缓存
     */
    void deleteTagCache();
}
