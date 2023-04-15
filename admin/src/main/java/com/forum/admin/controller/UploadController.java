package com.forum.admin.controller;

import com.forum.admin.service.UploadImageService;
import com.forum.common.result.Result;
import com.forum.model.vo.ResultImage;
import com.forum.model.vo.ResultImageFail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * @auther 尚智江
 * @Date 2023/4/3 21:50
 */
@RestController
@CrossOrigin
public class UploadController {
    @Autowired
    private UploadImageService uploadImageService;

    /**
     * 测试
     * @return
     */
    @GetMapping("/hello")
    public String a(){
        return "a";
    }

    /**
     * 上传图片
     * @param file 文件
     * @return result
     */
    @PostMapping("/image")
    public Result upLoadImage(@RequestParam("file") MultipartFile file){
        String result = "失败";
        HashMap<String,String> map = new HashMap<>();
        if(!file.isEmpty()){
            String path = uploadImageService.uploadQNImg(file);
            if(path.equals(result)){
                return Result.fail();
            }else{
                map.put("imageUrl",path);
                return Result.ok(map);
            }
        }
        return Result.fail();
    }

    @PostMapping("/wzuploadImage")
    public ResultImage WzUploadImage(@RequestParam("file") MultipartFile file) {
        HashMap<String,Object> map = new HashMap<>();
        if (!file.isEmpty()){
            String path = uploadImageService.uploadQNImg(file);
            if (path.equals("失败")){
                ResultImage resultImage = new ResultImage(3,"上传失败");
                return resultImage;
            }else {
                map.put("url",path);
                ResultImage resultImage = new ResultImage(0,map);
                return resultImage;
            }
        }
        return new ResultImage(3,"上传失败");
    }

}
