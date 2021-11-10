package com.lht.admin.service.impl;

import com.lht.admin.service.IRedisService;
import com.lht.admin.vo.PvAndUvVo;
import com.lht.admin.vo.ViewsDateVo;
import com.lht.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author lht
 * @date 2021/10/30 - 18:55
 * @description: redis服务实现类
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${spring.cache.redis.key-prefix}")
    public String CACHE_PRE;

    @Override
    public PvAndUvVo getPvAndUv() {
        PvAndUvVo pvAndUvVo = new PvAndUvVo();
        Long pv = Long.valueOf((Integer) redisTemplate.opsForValue().get(RedisConstant.VIEW_KEY));
        Long uv = redisTemplate.opsForHyperLogLog().size(RedisConstant.IP_KEY);
        pvAndUvVo.setPv(pv);
        pvAndUvVo.setUv(uv);
        return pvAndUvVo;
    }

    @Override
    @Async
    public void increment(String ip) {
        // PV
        redisTemplate.opsForValue().increment(RedisConstant.VIEW_KEY);
        // UV
        redisTemplate.opsForHyperLogLog().add(RedisConstant.IP_KEY, ip);
        // 保存过去半个月里的访问量增加情况
        String todayKey = RedisConstant.TODAY_VIEWS_PRE + LocalDate.now();
        if(!redisTemplate.hasKey(todayKey)) {
            redisTemplate.opsForValue().set(todayKey, 0);
            // 过期时间半个月
            redisTemplate.expire(todayKey, RedisConstant.HALF_MONTH, TimeUnit.DAYS);
            return;
        }
        redisTemplate.opsForValue().increment(todayKey);
    }

    @Override
    public ViewsDateVo halfMonthViewInfo() {
        List<Integer> counts = new ArrayList<>(RedisConstant.HALF_MONTH);
        List<LocalDate> days = new ArrayList<>(RedisConstant.HALF_MONTH);
        for (int i = RedisConstant.HALF_MONTH; i >= 0; i--) {
            String key = RedisConstant.TODAY_VIEWS_PRE + LocalDate.now().minusDays(i);
            if (redisTemplate.hasKey(key)) {
                Integer count = (Integer) redisTemplate.opsForValue().get(key);
                counts.add(count);
                days.add(LocalDate.now().minusDays(i));
            }
        }
        ViewsDateVo viewsDateVo = new ViewsDateVo();
        viewsDateVo.setCounts(counts);
        viewsDateVo.setDays(days);
        return viewsDateVo;
    }

    @Override
    @Async
    public void deleteTagCache() {
        Set<String> keys = redisTemplate.keys(CACHE_PRE + "Tag*");
        redisTemplate.delete(keys);
    }

}
