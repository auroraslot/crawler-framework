package com.aurora.meta.crawler.constants;

/**
 * @author irony
 */
public class OssConstant {
    /**
     * OSS URL过期时间，永不过期；如果没有指定，会随机生成一个过期时间
     */
    public static final Long ONE_HUNDRED_YEARS = 50 * 360 * 24 * 3600 * 1000L;

    /**
     * 存储元数据的OSS bucketName
     */
    public static final String META_DEFAULT_BUCKET_NAME = "aurora-meta-oss";

    /**
     * 作者头像元数据OSS路径
     */
    public static final String AUTHOR_AVATAR_OSS_PATH = "authorAvatar/";

    /**
     * SPU头像元数据OSS路径
     */
    public static final String SPU_AVATAR_OSS_PATH = "spuAvatar/";
}
