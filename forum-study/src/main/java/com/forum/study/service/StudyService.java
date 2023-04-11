package com.forum.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.model.pojo.Study;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 尚智江
 * @Date 2023/4/5 19:36
 */
@Service
public interface StudyService extends IService<Study> {
    // 根据数据id查询子数据id
    List<Study> findChlidData(Long id);
}
