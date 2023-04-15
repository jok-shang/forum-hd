package com.forum.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
//import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @auther 尚智江
 * @Date 2023/4/4 10:35
 */
@TableName("wenzhang")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WenZhang  {
    @TableId(value = "tid",type = IdType.AUTO)
    private Integer tid;

    @TableField("uid")
    private Integer uid;

    @TableField("sid")
    private Integer sid;

    @TableField("biaoti")
    private String biaoTi;

    @TableField("neirong")
    private String neiRong;

    @TableField(value = "createtime",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "updatetime" ,fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableLogic(value = "0",delval = "1")
    @TableField(value = "isdelete",fill = FieldFill.INSERT)
    private Integer isDelete;

    @TableField("tstart")
    private Integer tStart;

    @TableField("tshou")
    private Integer tShou;

}
