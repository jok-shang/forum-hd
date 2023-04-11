package com.forum.util.utils;

import java.util.UUID;

/**
 * @auther 尚智江
 * @Date 2023/4/3 21:28
 */
public class StringUtil {
    public static String getRandomImgName(String fileName) {

        // 获取文件后缀
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);

        //检验文件
        if(".jpg".equals(suffix) || ".jpeg".equals(suffix) || ".png".equals(suffix)){
            //改变上传到服务器的文件名  uuid + suffix
            // 生成UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String path = uuid + suffix;
            return path;
        }else{
            throw new IllegalArgumentException();
        }
    }
}
