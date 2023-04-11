package com.forum.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 尚智江
 * @Date 2023/4/11 0:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenVO {
    /** 用户名 */
    private String username;

    /** 用户邮箱 */
    private String email;

    /** 用户头像链接 */
    private String headImage;

    /** token */
    private String token;
}
