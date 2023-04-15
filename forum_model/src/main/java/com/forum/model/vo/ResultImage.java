package com.forum.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @auther 尚智江
 * @Date 2023/4/15 14:29
 */
@Data

@NoArgsConstructor
public class ResultImage<T> implements Serializable {

    private Integer errno;
    private T data;

    public ResultImage(Integer errno, T data) {
        this.errno = errno;
        this.data = data;
    }

}
