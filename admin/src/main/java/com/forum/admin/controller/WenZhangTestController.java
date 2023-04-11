package com.forum.admin.controller;

import com.forum.admin.service.WtestService;
import com.forum.admin.service.impl.WenZhangServiceImpl;
import com.forum.common.result.Result;
import com.forum.common.result.ResultCodeEnum;
import com.forum.model.pojo.WenZhang;
import com.forum.model.vo.WenZhangInsertVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther 尚智江
 * @Date 2023/4/4 10:51
 */
@RestController
@CrossOrigin
public class WenZhangTestController {

    @Autowired
    private WtestService wtestService;

    @PostMapping("/insert")
    public Result insert(@RequestBody WenZhang wenZhang){
        boolean save = wtestService.save(wenZhang);
        System.out.println("---------"+save);
        System.out.println(wenZhang.getBiaoTi()+wenZhang.getNeiRong());
        if (save)
            return Result.ok(wenZhang);
        else return Result.fail();
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<WenZhang> list = wtestService.list();
        System.out.println(list);
        return Result.ok(list);
    }
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
        WenZhang byId = wtestService.getById(id);
        System.out.println(byId+"------");
        if (byId == null) return Result.build(byId,ResultCodeEnum.PARAM_ERROR);
        if (!StringUtils.isEmpty(id)){
            return Result.ok(byId);
        }
        return Result.fail();
    }
}
