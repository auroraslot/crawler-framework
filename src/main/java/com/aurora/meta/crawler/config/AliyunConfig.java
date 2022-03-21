package com.aurora.meta.crawler.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aurora.meta.crawler.manager.OssManager;
import com.aurora.meta.crawler.manager.impl.OssManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author zhonghuashishan
 */
@ConditionalOnProperty(prefix = "aliyun.meta")
public class AliyunConfig {
    /**
     * 阿里云 accessKey
     */
    private String accessKeyId;

    /**
     * 阿里云 secret
     */
    private String accessKeySecret;

    /**
     * OSS 域名
     */
    private String endpoint;

    /**
     * 上传文件回调地址
     */
    private String callbackUrl;

    /**
     * 创建阿里云OSS客户端
     *
     * @return OSS客户端
     */
    @Bean
    @Scope("prototype")
    public OSS initOssClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * OSS资源管理器
     * @param initOssClient
     * @return
     */
    @Bean
    public OssManager ossManager(OSS initOssClient) {
        OssManager ossManager = new OssManagerImpl(endpoint, accessKeyId, callbackUrl, initOssClient);
        return ossManager;
    }
}
