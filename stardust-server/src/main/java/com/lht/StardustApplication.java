package com.lht;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 * @author lht
 */
@SpringBootApplication
@MapperScan("com.lht.admin.mapper")
public class StardustApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(StardustApplication.class,args);
    }
}
