package com.forum.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @auther 尚智江
 * @Date 2023/4/3 21:37
 */
@SpringBootApplication
@MapperScan({"com.forum.admin.mapper","com.forum.study.mapper","com.forum.user.mapper"})
@ComponentScan({"com.forum"})  // 扫描其他子模块
public class ForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class);
    }
}
