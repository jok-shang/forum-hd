package com.forum.user.controller;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forum.common.result.Result;
import com.forum.common.result.ResultCodeEnum;
import com.forum.model.pojo.User;
import com.forum.model.vo.UserTokenVO;
import com.forum.model.vo.UserUPPasswordVO;
import com.forum.model.vo.UserVO;
import com.forum.user.mapper.UserMapper;
import com.forum.user.service.UserService;
import com.forum.util.MD5Utils.MD5Util;
import com.forum.util.jwtUtils.JwtUtil;
import com.forum.util.utils.EmailUtils;
import org.apache.ibatis.annotations.Param;
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
     * @param user 用户信息（用户名和邮箱）
     * @return Result
     * 获取前端传的 用户名和邮箱，向邮箱中发送验证码
     */
    @PostMapping("/insertUser1")
    public Result insertUser(@RequestBody User user) {
        // TODO 查询用户名是否存在
        int count = userService.count(new QueryWrapper<User>().eq("username",user.getUsername()));
        // TODO 查看返回影响行数 >0 说明用户已经存在 用户名唯一
        if (count > 0){
            return Result.fail().message("用户名重复");
        }
        // TODO 调用 servie -》向注册用户发送验证码
        if (userService.fsYzm(user)){
            return Result.ok().message("验证码发送成功");
        }
        return Result.fail().message("验证码发送异常");
    }

    /**
     * 将表单中的用户数据添加到表单
     * @param userVO 用户VO信息（多验证码字段在数据库不存在在实体类中用注解表示和redis中发送给用户邮箱的验证码做匹配）
     * @return Result
     */
    @PostMapping("/insertUser2")
    public Result insertUser2(@RequestBody UserVO userVO) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        // TODO 获取redis中的 K email V 验证码
        String yzm = operations.get(userVO.getEmail());
//        System.out.println("验证码"+yzm+"===="+userVO.getYzm()+yzm.equals(userVO.getYzm()));
        // TODO 对比用户输入的验证码和redis中的验证码是否一致
        if (yzm.equals(userVO.getYzm())) {
            User user = new User();
            //TODO md5 用户密码加密
            MD5Util md5Util = new MD5Util();
            //TODO 用户输入的密码（加密前密码）
            String oldPassword = userVO.getPassword();
            //TODO 随机生成盐值
            String salt = UUID.randomUUID().toString().toUpperCase();
            //TODO 调用md5加密方法（加密后密码）
            String md5Password = md5Util.getMD5Password(oldPassword, salt);
            //TODO 补全用户提交信息（补全用户其他信息）
            user.setUsername(userVO.getUsername());
            user.setPassword(md5Password);
            user.setEmail(userVO.getEmail());
            user.setHeadImage("http://image.szj.icu/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20220607211837.jpg");
            user.setIsDelete(0);
            user.setSalt(salt);
            // TODO 判断用户信息是否保存完成
            boolean save = userService.save(user);
            return save ? Result.ok().message("注册成功") : Result.fail().message("注册失败");
            // TODO 验证码输入错误
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
            // TODO 查询用户名和email两个条件符合的用户
            User one = userService.getOne(queryWrapper);
            // TODO 用户输入密码加密步骤
            String oldPassword = one.getPassword();
            String salt = one.getSalt();
            MD5Util md5Util = new MD5Util();
            String md5Password = md5Util.getMD5Password(user.getPassword(), salt);
            // TODO 判断用户输入密码和数据库中的密码加密后是否一致
            if (oldPassword.equals(md5Password)) {
                // TODO 生成token
                JwtUtil jwtUtil = new JwtUtil();
                String token = jwtUtil.creatToken(one);
//            System.out.println(token+"token");
                //TODO 向UserTokenVo中添加token信息
                one.setToken(token);
                UserTokenVO userTokenVO = new UserTokenVO(one.getUid(),one.getUsername(),
                        one.getEmail(),one.getHeadImage(),one.getToken());
                // TODO 规定向前端返回的json
                return Result.ok(userTokenVO).message("登录成功");
            }
            return Result.fail().message("登录账户/密码错误");
        } catch (Exception e) {
            Result.fail().message("账号异常");
        }
        return Result.fail().message("账号异常");
    }

    /**
     * 前端使用路由守卫判断token是否过期
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

    /**
     * 修改密码1  向用户的邮箱发送验证码
     * @param email  邮箱
     * @return Result
     */
    @GetMapping("/updatePassword1")
    public Result updatePassword1(@Param("email") String email){
        // TODO 判断修改密码的验证码是否发送成功
        boolean b = userService.fsYzmUP(email);
        return b ? Result.ok().message("验证码发送成功") : Result.fail().message("验证码发送异常,请检查邮箱信息");
    }

    /**
     * 修改用户密码提交表单
     * @param userUPPasswordVO
     * @return
     */
    @PostMapping("/updatePasswoerd2")
    public Result updatePassword2(@RequestBody UserUPPasswordVO userUPPasswordVO){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        // TODO 查询redis中的验证码
        String yzm = operations.get(userUPPasswordVO.getEmail());
//        System.out.println(userUPPasswordVO.getEmail()+"////"+yzm);
        // TODO 判断用户输入的验证码和redis中是否一致
        if (yzm.equals(userUPPasswordVO.getYzm())){
//            System.out.println(yzm+"===="+userUPPasswordVO.getYzm());
            boolean b = userService.updatePassword(userUPPasswordVO);
            // TODO 判断用户修改是否成功
            if (b){
                return Result.ok().message("修改成功");
            }
            return Result.fail().message("修改密码异常");
        }
        System.out.println(yzm.equals(userUPPasswordVO.getEmail()));
        return Result.fail().message("验证码错误");
    }
}
