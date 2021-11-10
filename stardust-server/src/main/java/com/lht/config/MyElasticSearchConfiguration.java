package com.lht.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lht
 * @date 2021/10/25 - 17:06
 * @description: es配置
 */
@Configuration
public class MyElasticSearchConfiguration {

    @Value("${es.host}")
    private String host;

    @Value("${es.port}")
    private Integer port;

    private static final String SCHEME = "http";

    public static final RequestOptions COMMON_OPTIONS;

    // 如果后面需要添加什么通用的配置可以添加
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost(host, port, SCHEME)
        ));

        return client;
    }
}
