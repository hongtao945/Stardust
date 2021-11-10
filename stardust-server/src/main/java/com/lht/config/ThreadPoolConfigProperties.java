package com.lht.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author lht
 * @date 2021/10/28 - 18:51
 * @description: 线程池配置
 */
@Data
@ConfigurationProperties(prefix = "stardust.thread")
public class ThreadPoolConfigProperties {

    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;

}
