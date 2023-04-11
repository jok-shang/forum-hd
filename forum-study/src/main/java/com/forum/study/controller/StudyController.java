package com.forum.study.controller;

import com.forum.common.result.Result;
import com.forum.model.pojo.Study;
import com.forum.study.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther 尚智江
 * @Date 2023/4/5 19:49
 */
@RestController
@CrossOrigin
//@RequestMapping("/study")
public class StudyController {

    @Autowired
    private StudyService studyService;

    @GetMapping("/study")
    public String study(){
        return "Study";
    }

    // 根据数据id查询子数据列表
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Study> list = studyService.findChlidData(id);
        return Result.ok(list);
    }
}
