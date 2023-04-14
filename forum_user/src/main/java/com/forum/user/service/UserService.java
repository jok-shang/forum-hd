package com.forum.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.model.pojo.User;
import com.forum.model.vo.UserUPPasswordVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther 尚智江
 * @Date 2023/4/8 23:57
 */
@Service
public interface UserService extends IService<User> {

    /**
     * 随机生成六位随机数字
     * @return
     */
    public String getRandomCount();

    // 注册发送验证码
    boolean fsYzm(User user);

    // 修改密码发送验证码
    boolean fsYzmUP(String email);

    // 修改用户密码
    boolean updatePassword(UserUPPasswordVO userUPPasswordVO);

}
