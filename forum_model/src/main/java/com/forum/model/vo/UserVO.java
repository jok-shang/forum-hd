package com.forum.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @auther 尚智江
 * @Date 2023/4/9 16:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    /** 用户名 */
    private String username;

    /** 用户密码 */
    private String password;

    /** 用户邮箱 */
    private String email;

    /** 用户头像链接 */
    private String headImage;


    /** md5加密盐值 */
    private String salt;

    /** 验证码 */
    private String yzm;
}
