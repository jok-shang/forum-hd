package com.forum.common.result;

import lombok.Getter;

/**
 * @auther 尚智江
 * @Date 2023/4/3 18:16
 */

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    REGISTER_SUCCESS(200,"注册成功"),
    FAIL(201, "失败"),

    PARAM_ERROR( 202, "参数不正确"),

    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    DATA_UPDATE_ERROR(205, "数据版本异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),

    CODE_ERROR(210, "验证码错误"),
    LOGIN_AURH(214, "需要登录"),
    LOGIN_ACL(215, "没有权限");

    private Integer code;

    private String message;

    ResultCodeEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
