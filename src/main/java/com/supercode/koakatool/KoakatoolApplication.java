package com.supercode.koakatool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //开启定时任务
//@ServletComponentScan//使用过滤器 servlet 监听器
@SpringBootApplication
@MapperScan("com.**.domain")
public class KoakatoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoakatoolApplication.class, args);
    }
}
