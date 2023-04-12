package com.forum.wenzhang.Controller;

import com.forum.common.result.Result;
import com.forum.model.pojo.Section;
import com.forum.model.pojo.WenZhang;
import com.forum.wenzhang.service.SectionService;
import com.forum.wenzhang.service.WenZhangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther 尚智江
 * @Date 2023/4/13 0:20
 */
@CrossOrigin
@RestController
public class WenZhangController {

    @Autowired
    private WenZhangService wenZhangService;
    @Autowired
    private SectionService sectionService;

    /**
     * 发布文章
     * @param wz 文章
     * @return
     */
    @PostMapping("/insert")
    public Result insertWenZhang(@RequestBody WenZhang wz){
        boolean save = wenZhangService.save(wz);
        if (save)
            return Result.ok().message("发布成功");
        return Result.fail().message("发布失败");
    }

    @GetMapping("/getBanKuai")
    public Result getBanKuai(){
        List<Section> list = sectionService.list();
        return Result.ok(list);
    }
}
