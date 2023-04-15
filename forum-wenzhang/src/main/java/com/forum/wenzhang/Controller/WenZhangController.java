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

    /**
     * 获取文章分类板块
     * @return
     */
    @GetMapping("/getBanKuai")
    public Result getBanKuai(){
        List<Section> list = sectionService.list();
        return Result.ok(list);
    }

    /**
     * 获取文章列表(所有)
     * @return
     */
    @GetMapping("/WZList")
    public Result getWenZhangList(){
        List<WenZhang> list = wenZhangService.list();
        return Result.ok(list);
    }

    /**
     * 根据id获取文章
     * @param id
     * @return
     */
    @GetMapping("/GetWZById/{id}")
    public Result getWenZhangById(@PathVariable("id") Integer id){
        WenZhang byId = wenZhangService.getById(id);
        return Result.ok(byId);
    }
}
