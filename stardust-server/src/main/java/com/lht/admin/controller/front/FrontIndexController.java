package com.lht.admin.controller.front;

import com.lht.admin.service.IRedisService;
import com.lht.admin.vo.PvAndUvVo;
import com.lht.constant.RedisConstant;
import com.lht.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author lht
 * @date 2021/4/15 - 23:38
 * @description:
 */
@RestController
@RequestMapping("/front")
@Api(tags = "前端博客-公共部分")
public class FrontIndexController {

    @Resource
    private IRedisService redisService;

    @ApiOperation(value = "增加PV和UV")
    @GetMapping("/increment")
    public void increment(HttpServletRequest request) {
        redisService.increment(StringUtils.getIp(request));
    }

    @ApiOperation(value = "获取PV和UV")
    @GetMapping("/pu")
    public PvAndUvVo getPvAndUv() {
        return redisService.getPvAndUv();
    }

}
