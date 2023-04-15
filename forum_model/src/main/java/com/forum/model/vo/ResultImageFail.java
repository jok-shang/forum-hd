package com.forum.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 尚智江
 * @Date 2023/4/15 15:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultImageFail {
    private Integer errno;
    private String message;

}
