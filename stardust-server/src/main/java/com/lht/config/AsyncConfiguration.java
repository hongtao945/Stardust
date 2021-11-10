package com.lht.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @Author lht
 * @date 2021/10/28 - 18:42
 * @description: 异步配置
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(com.lht.config.ThreadPoolConfigProperties.class)
public class AsyncConfiguration implements AsyncConfigurer{

    @Resource
    ThreadPoolConfigProperties properties;

    @Override
    @Bean
    public Executor getAsyncExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(properties.getCoreSize(), properties.getMaxSize(),
                properties.getKeepAliveTime(), TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(Runtime.getRuntime().availableProcessors() * 2), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        return threadPoolExecutor;
    }

}
