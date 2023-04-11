package com.forum.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.common.result.Result;
import com.forum.user.mapper.UserMapper;
import com.forum.user.service.UserService;
import com.forum.model.pojo.User;
import com.forum.util.MD5Utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

/**
 * @auther 尚智江
 * @Date 2023/4/8 23:58
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 随机生成六位随机数字
     * @return
     */
    @Override
    public String getRandomCount() {
        Random random = new Random();
        StringBuilder value = new StringBuilder(String.valueOf(random.nextInt(9) + 1));
        for (int i = 0; i < 5; i++){
            value.append(random.nextInt(10));
        }
        return value.toString();
    }



}
