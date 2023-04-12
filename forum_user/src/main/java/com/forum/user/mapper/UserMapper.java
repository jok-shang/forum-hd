package com.forum.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther 尚智江
 * @Date 2023/4/8 23:55
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
//    User selectAllUser(String email);
//    Integer selectUsername(@Param("username") String username);
}
