package com.forum.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.model.pojo.User;
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

    boolean fsYzm(User user);
//    boolean selectUsername(String username);

}
