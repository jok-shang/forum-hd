package com.forum.study.controller;

import com.forum.common.result.Result;
import com.forum.model.pojo.Study;
import com.forum.model.pojo.StudyNeiRong;
import com.forum.study.service.StudyNeiRongService;
import com.forum.study.service.StudyService;
import org.apache.ibatis.annotations.Param;
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
    @Autowired
    private StudyNeiRongService studyNeiRongService;


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


    // 向学习资源页面添加内容
    @PostMapping("/insertStudy")
    public Result insertNeiRong(@Param("sneirong") String sneirong){
        StudyNeiRong studyNeiRong = new StudyNeiRong();
        studyNeiRong.setSneirong(sneirong);
        boolean save = studyNeiRongService.save(studyNeiRong);
        return save ? Result.ok().message("插入成功") : Result.fail().message("插入失败");
    }

    /**
     * 根据id查询学习页面内容
     * @param id
     * @return
     */
    @GetMapping("getStudyById/{id}")
    public Result getById(@PathVariable Integer id){
        StudyNeiRong byId = studyNeiRongService.getById(id);
        return Result.ok(byId);
    }
}
