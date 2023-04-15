package com.forum.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @auther 尚智江
 * @Date 2023/4/15 16:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("shoucang")
public class ShouCang {

    @TableId("cid")
    private Integer cid;
    @TableField("uid")
    private Integer uid;
    @TableField("tid")
    private Integer tid;
    @TableField("create_time")
    private Date createTime;
    @TableField("isdelete")
    private Integer isDelete;
}
