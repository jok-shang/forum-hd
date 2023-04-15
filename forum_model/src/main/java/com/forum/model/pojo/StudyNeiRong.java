package com.forum.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 尚智江
 * @Date 2023/4/15 20:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("study_neirong")
public class StudyNeiRong {

    @TableId(value = "sid",type = IdType.AUTO)
    private Integer sid;
    @TableField("sneirong")
    private String sneirong;
}
