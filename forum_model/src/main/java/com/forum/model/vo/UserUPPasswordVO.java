package com.forum.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 尚智江
 * @Date 2023/4/14 15:33
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUPPasswordVO {

    private String username;
    private String email;
    private String password;
    private String yzm;

}
