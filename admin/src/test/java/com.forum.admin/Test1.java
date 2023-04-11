package com.forum.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.model.pojo.User;
import com.forum.user.mapper.UserMapper;
import com.forum.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @auther 尚智江
 * @Date 2023/4/9 19:51
 */
@SpringBootTest
public class Test1 {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @org.junit.jupiter.api.Test
    public void a() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String a = "abc";
        String c = "3229699520@qq.com";
        queryWrapper.eq("username", c).or().eq("email", c);

        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);

    }
}
