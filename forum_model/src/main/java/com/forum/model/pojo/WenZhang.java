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
 * @Date 2023/4/4 10:35
 */
@TableName("wtest")
public class WenZhang  {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("biaoti")
    private String biaoTi;
    @TableField("neirong")
    private String neiRong;

    public WenZhang() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBiaoTi() {
        return biaoTi;
    }

    public void setBiaoTi(String biaoTi) {
        this.biaoTi = biaoTi;
    }

    public String getNeiRong() {
        return neiRong;
    }

    public void setNerRong(String neiRong) {
        this.neiRong = neiRong;
    }

    public WenZhang(Integer id, String biaoTi, String neiRong) {
        this.id = id;
        this.biaoTi = biaoTi;
        this.neiRong = neiRong;
    }
}
