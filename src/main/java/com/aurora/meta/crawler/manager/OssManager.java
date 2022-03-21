package com.aurora.meta.crawler.manager;

import com.aurora.meta.crawler.enums.OssPathEnum;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author zhonghuashishan
 */
public interface OssManager {
    /**
     * 上传文件
     * 返回的是文件下载地址
     *
     * @param bucketName      bucketName
     * @param fileInputStream 文件输入流
     * @param path            目录
     * @param fileName        文件名字
     * @return 文件下载地址
     * @throws Exception
     */
    String uploadFile(String bucketName, FileInputStream fileInputStream, String path, String fileName);

    /**
     * 上传文件
     * 返回的是文件下载地址
     *
     * @param fileInputStream 文件输入流
     * @param path            目录
     * @param fileName        文件名字
     * @return 文件下载地址
     * @throws Exception
     */
    @Deprecated
    String uploadFile(FileInputStream fileInputStream, String path, String fileName);

    /**
     * 上传文件，分片上传
     *
     * @param bucketName    buckName
     * @param ossObjectName oss中文件的名字（包含路径）
     * @param file          文件对象
     * @return 文件下载地址
     * @throws Exception
     */
    String uploadFile(String bucketName, String ossObjectName, File file) throws Exception;

    /**
     * 生成文件下载地址
     *
     * @param bucketName bucketName
     * @param fileName oss中文件的名字（包含路径）
     * @param expirationTime URL过期时间
     * @return 下载url地址
     */
    String generateFileDownLoadUrl(String bucketName, String fileName, long expirationTime) throws Exception;

    /**
     * 生成文件下载地址
     *
     * @param fileName oss中文件的名字（包含路径）
     * @param expirationTime URL过期时间
     * @return 下载url地址
     */
    @Deprecated
    String generateFileDownLoadUrl(String fileName, long expirationTime) throws Exception;

    /**
     * 生成文件下载地址
     *
     * @param bucketName bucketName
     * @param fileName oss中文件的名字（包含路径）
     * @return 下载url地址
     */
    String generateFileDownLoadUrl(String bucketName, String fileName);

    /**
     * 生成文件下载地址
     *
     * @param fileName oss中文件的名字（包含路径）
     * @return 下载url地址
     */
    @Deprecated
    String generateFileDownLoadUrl(String fileName);

    /**
     * 授权上传
     *
     * @param ossPathEnum oss存放路径
     * @param bucketName bucketName
     */
    String authorizeUpload(OssPathEnum ossPathEnum, String bucketName);

    /**
     * 授权上传
     *
     * @param ossPathEnum oss存放路径
     */
    @Deprecated
    String authorizeUpload(OssPathEnum ossPathEnum);
}
