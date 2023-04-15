package com.forum.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther 尚智江
 * @Date 2023/4/3 21:38
 */
@Service
public interface UploadImageService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    String uploadQNImg(MultipartFile file);
    public String getPrivateFile(String fileKey);
//    public boolean removeFile(String bucketName, String fileKey);
    }
