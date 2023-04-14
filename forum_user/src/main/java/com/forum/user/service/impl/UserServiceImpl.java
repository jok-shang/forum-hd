package com.forum.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.common.result.Result;
import com.forum.model.vo.UserUPPasswordVO;
import com.forum.user.mapper.UserMapper;
import com.forum.user.service.UserService;
import com.forum.model.pojo.User;
import com.forum.util.MD5Utils.MD5Util;
import com.forum.util.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @auther 尚智江
 * @Date 2023/4/8 23:58
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

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

    @Override
    public boolean fsYzm(User user) {
        //TODO 生成6位随机整数
        String randomCount = this.getRandomCount();
        //TODO 向用户邮箱发送验证码
        EmailUtils emailUtils = new EmailUtils();
        try {
            emailUtils.toEmail(user.getEmail(), "欢迎" + user.getUsername() +
                    "注册学习论坛平台", "您的注册验证码为:   " + randomCount);
        } catch (MessagingException e) {
            return false;
        }
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(user.getEmail(),randomCount, 3, TimeUnit.MINUTES);
        return true;
    }

    /**
     * 修改密码发送验证码
     * @param email 用户邮箱
     * @return Result
     */
    @Override
    public boolean fsYzmUP(String email) {
        //TODO 生成6位随机整数
        String randomCount = this.getRandomCount();
        //TODO 向用户邮箱发送验证码
        EmailUtils emailUtils = new EmailUtils();
        try {
            emailUtils.toEmail(email, "修改学习论坛平台密码" ,"您本次修改账号密码的验证码为 : "+ randomCount);
        } catch (MessagingException e) {
            return false;
        }
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
//        TimeUnit.MINUTES
        operations.set(email,randomCount, 3, TimeUnit.MINUTES);
        return true;
    }

    /**
     * 修改密码
     * @param userUPPasswordVO 修改密码的VO类
     * @return Result
     */
    @Override
    public boolean updatePassword(UserUPPasswordVO userUPPasswordVO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",userUPPasswordVO.getEmail()).eq("username",userUPPasswordVO.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        System.out.println("查询出来的用户信息"+user);
        if (user != null){
            String salt = UUID.randomUUID().toString().toUpperCase();
            MD5Util md5Util = new MD5Util();
            String newPassword = md5Util.getMD5Password(userUPPasswordVO.getPassword(), salt);
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .eq("username",userUPPasswordVO.getUsername());
            updateWrapper.set("salt",salt);
            updateWrapper.set("password",newPassword);
            int update = baseMapper.update(user,updateWrapper);
            System.out.println("修改影响行数"+update);
            return update > 0;
        }
        return false;
    }

//    public Result updatePassword()

//    @Override
//    public boolean selectUsername(String username) {
//        Integer integer = userMapper.selectUsername(username);
//        return integer > 0;
//    }


}
