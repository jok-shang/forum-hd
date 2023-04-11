package com.forum.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @auther 尚智江
 * @Date 2023/4/4 12:28
 */
@Data
public class WenZhangInsertVO {

    /** 文章标题 */
    private String biaoTi;
    /** 文章内容 */
    private String neiRong;

}
