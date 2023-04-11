package com.forum.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @auther 尚智江
 * @Date 2023/4/5 19:28
 */
@Data
@TableName("study_direct")
public class Study {

    @TableId
    private Long sid;

    @TableField("sname")
    private String sname;

    @TableField("parent_id")
    private Long parentId;

    @TableField("value")
    private Long value;

    @TableField(exist = false)
    private boolean hasChildren;

    public Study() {
    }

    public Study(Long sid, String sname, Long parentId, Long value, boolean hasChildren) {
        this.sid = sid;
        this.sname = sname;
        this.parentId = parentId;
        this.value = value;
        this.hasChildren = hasChildren;
    }
}
