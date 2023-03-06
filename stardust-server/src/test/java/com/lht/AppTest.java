package com.lht;

import com.lht.admin.service.IElasticSearchService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest
{

    @Resource
    private IElasticSearchService elasticSearchService;

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void shouldAnswerWithTrue()
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

    @Test
    public void esIndexTest() {
        elasticSearchService.sync();
    }

    @Test
    public void esDeleteAllTest() {
        elasticSearchService.deleteAll();
    }

    @Test
    public void redisDelete() {
        Set<String> keys = redisTemplate.keys("CACHE_Cl" + "*");
        redisTemplate.delete(keys);
    }

    @Test
    public void searchTest() {
        elasticSearchService.search("月");
    }

}
