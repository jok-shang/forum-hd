package com.forum.user.controller;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.common.result.Result;
import com.forum.common.result.ResultCodeEnum;
import com.forum.model.pojo.User;
import com.forum.model.vo.UserTokenVO;
import com.forum.model.vo.UserVO;
import com.forum.user.mapper.UserMapper;
import com.forum.user.service.UserService;
import com.forum.util.MD5Utils.MD5Util;
import com.forum.util.jwtUtils.JwtUtil;
import com.forum.util.utils.EmailUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @auther 尚智江
 * @Date 2023/4/9 0:00
 */
@RestController
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 测试使用查看用户的验证码是否过期
     * @param user 用户信息
     * @return Result
     */
    @GetMapping("/user")
    public String user(@RequestBody User user) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        //TODO 生成随机6位整数
        String randomCount = userService.getRandomCount();
        //TODO 向redis设置key：用户邮箱 , 随机出的验证码 , 设置失效时长 , 时间类型
        // SECONDS：时间单元代表1秒 ，  MINUTES：分钟时间单元代表60秒
//        operations.set("hello",randomCount,30, TimeUnit.SECONDS);
        String hello = operations.get(user.getEmail());
        System.out.println(hello);
        return "user验证码:  " + hello;
    }


    /**
     * 注册用户  发送验证码
     *
     * @param user 用户信息（用户名和邮箱）
     * @return Result
     * 获取前端传的 用户名和邮箱，向邮箱中发送验证码
     */
    @PostMapping("/insertUser1")
    public Result insertUser(@RequestBody User user) {
        // TODO 查询用户名是否存在
        int count = userService.count(new QueryWrapper<User>().eq("username",user.getUsername()));
        if (count > 0){
            return Result.fail().message("用户名重复");
        }
        if (userService.fsYzm(user)){
            return Result.ok().message("验证码发送成功");
        }
        return Result.fail().message("验证码发送异常");
    }

    /**
     * 将表单中的用户数据添加到表单
     * @param userVO 用户VO信息（多验证码字段，不存数据库和redis中发送给用户邮箱的验证码做匹配）
     * @return Result
     */
    @PostMapping("/insertUser2")
    public Result insertUser2(@RequestBody UserVO userVO) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String yzm = operations.get(userVO.getEmail());
//        System.out.println("验证码"+yzm+"===="+userVO.getYzm()+yzm.equals(userVO.getYzm()));
        if (yzm.equals(userVO.getYzm())) {
            User user = new User();
            //TODO md5 用户密码加密
            MD5Util md5Util = new MD5Util();
            //TODO 用户输入的密码
            String oldPassword = userVO.getPassword();
            //TODO 随机生成盐值
            String salt = UUID.randomUUID().toString().toUpperCase();
            //TODO 调用md5加密方法
            String md5Password = md5Util.getMD5Password(oldPassword, salt);
            //TODO 补全用户提交信息
            user.setUsername(userVO.getUsername());
            user.setPassword(md5Password);
            user.setEmail(userVO.getEmail());
            user.setHeadImage("http://image.szj.icu/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20220607211837.jpg");
            user.setIsDelete(0);
            user.setSalt(salt);
            boolean save = userService.save(user);
            return save ? Result.ok().message("注册成功") : Result.fail().message("注册失败");
        } else return Result.build(null, ResultCodeEnum.CODE_ERROR);
    }

    /**
     * 登录向前端发送token
     * @param user user
     * @return result
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername()).or().eq("email", user.getEmail());
        try {
            User one = userService.getOne(queryWrapper);
            String oldPassword = one.getPassword();
            String salt = one.getSalt();
            MD5Util md5Util = new MD5Util();
            String md5Password = md5Util.getMD5Password(user.getPassword(), salt);
            if (oldPassword.equals(md5Password)) {
                // TODO 生成token
                JwtUtil jwtUtil = new JwtUtil();
                String token = jwtUtil.creatToken(one);
//            System.out.println(token+"token");
                //TODO 向UserTokenVo中添加token信息
                one.setToken(token);
                UserTokenVO userTokenVO = new UserTokenVO(one.getUid(),one.getUsername(),
                        one.getEmail(),one.getHeadImage(),one.getToken());
                return Result.ok(userTokenVO).message("登录成功");
            }
            return Result.fail().message("登录账户/密码错误");
        } catch (Exception e) {
            Result.fail().message("账号异常");
        }
        return Result.fail().message("账号异常");
    }

    /**
     * 检查token是否过期
     * @param request 头请求
     * @return result
     */
    @GetMapping("/checkToken")
    public Result checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        JwtUtil jwtUtil = new JwtUtil();
        boolean pdToken = jwtUtil.checkToken(token);
        if (pdToken){
            return Result.ok();
        }
        return Result.build(pdToken,ResultCodeEnum.LOGIN_AURH);
    }


}
