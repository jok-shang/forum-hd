package com.forum.wenzhang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.model.pojo.Section;
import com.forum.wenzhang.mapper.SectionMapper;
import com.forum.wenzhang.service.SectionService;
import org.springframework.stereotype.Service;

/**
 * @auther 尚智江
 * @Date 2023/4/13 0:53
 */
@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section> implements SectionService {
}
