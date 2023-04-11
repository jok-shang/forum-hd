package com.forum.admin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @auther 尚智江
 * @Date 2023/4/3 21:48
 */
@Data
@Configuration
public class QiNiuYunConfig {
    @Value("${oss.qiniu.url}")
    private String url;

    @Value("${oss.qiniu.accessKey}")
    private String AccessKey;

    @Value("${oss.qiniu.secretKey}")
    private String SecretKey;

    @Value("${oss.qiniu.bucketName}")
    private String BucketName;
}
