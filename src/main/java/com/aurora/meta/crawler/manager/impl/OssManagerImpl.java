package com.aurora.meta.crawler.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.aurora.meta.crawler.enums.OssPathEnum;
import com.aurora.meta.crawler.manager.OssManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.aurora.meta.crawler.constants.OssConstant.META_DEFAULT_BUCKET_NAME;
import static com.aurora.meta.crawler.constants.OssConstant.ONE_HUNDRED_YEARS;

/**
 * @author irony
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class OssManagerImpl implements OssManager {

    private String endpoint;

    private String accessKey;

    private String callbackUrl;

    private ApplicationContext context;

    /**
     * 上传文件
     * 返回的是文件下载地址
     *
     * @param bucketName      bucketName
     * @param fileInputStream 文件输入流
     * @param path            目录
     * @param fileName        文件名字
     * @return 文件下载地址
     */
    @Override
    public String uploadFile(String bucketName, InputStream fileInputStream, String path, String fileName) {
        try {
            OSS ossClient = context.getBean(OSS.class);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentDisposition("attachment; filename=\"" + fileName + "\"");
            ossClient.putObject(bucketName, path + fileName, fileInputStream, metadata);
            ossClient.shutdown();
            return generateFileDownLoadUrl(bucketName, path + fileName);
        } catch (OSSException | ClientException e) {
            log.error("uploadFile error,fileName:{},path:{},", fileName, path, e);
            throw e;
        }
    }

    /**
     * 上传文件
     * 返回的是文件下载地址
     *
     * @param fileInputStream 文件输入流
     * @param path            目录
     * @param fileName        文件名字
     * @return 文件下载地址
     */
    @Override
    public String uploadFile(InputStream fileInputStream, String path, String fileName) {
        return uploadFile(META_DEFAULT_BUCKET_NAME, fileInputStream, path, fileName);
    }

    /**
     * 上传文件
     * 返回的是文件下载地址
     *
     * @param originUrl 源URL
     * @param path      目录
     * @param fileName  文件名字
     * @return 文件下载地址
     */
    @Override
    public String uploadFile(String originUrl, String path, String fileName) {
        try {
            URL url = new URL(originUrl);
            URLConnection con = url.openConnection();
            InputStream fileInputStream = con.getInputStream();
            return uploadFile(fileInputStream, path, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件，分片上传
     *
     * @param bucketName    buckName
     * @param ossObjectName oss中文件的名字（包含路径）
     * @param file          文件对象
     * @return 文件下载地址
     * @throws Exception
     */
    @Override
    public String uploadFile(String bucketName, String ossObjectName, File file) throws Exception {
        OSS ossClient = context.getBean(OSS.class);
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, ossObjectName);
        // 如果需要在初始化分片时设置文件存储类型，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // request.setObjectMetadata(metadata);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentDisposition("attachment; filename=\"" + file.getName() + "\"");
        request.setObjectMetadata(metadata);
        // 初始化分片。
        InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);

        // 返回uploadId，它是分片上传事件的唯一标识，您可以根据这个uploadId发起相关的操作，如取消分片上传、查询分片上传等。
        String uploadId = upresult.getUploadId();
        // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
        List<PartETag> partETags = new ArrayList<>();
        // 计算文件有多少个分片。
        final long partSize = 2 * 1024 * 1024L;   // 2MB
        long fileLength = file.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        // 遍历分片上传。
        for (int i = 0; i < partCount; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            InputStream instream = new FileInputStream(file);
            // 跳过已经上传的分片。
            instream.skip(startPos);
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(ossObjectName);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setInputStream(instream);
            // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
            uploadPartRequest.setPartSize(curPartSize);
            // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
            uploadPartRequest.setPartNumber(i + 1);
            // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
            // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
            partETags.add(uploadPartResult.getPartETag());
        }

        // 创建CompleteMultipartUploadRequest对象。
        // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, ossObjectName, uploadId, partETags);
        // 如果需要在完成文件上传的同时设置文件访问权限，请参考以下示例代码。
        // completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.PublicRead);

        // 完成上传。
        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
        return generateFileDownLoadUrl(bucketName, ossObjectName);
    }

    /**
     * 生成文件下载地址
     *
     * @param bucketName bucketName
     * @param fileName oss中文件的名字（包含路径）
     * @return 下载url地址
     */
    @Override
    public String generateFileDownLoadUrl(String bucketName, String fileName, long expirationTime) {
        try {
            Date expiration = new Date(new Date().getTime() + expirationTime);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            OSS ossClient = context.getBean(OSS.class);
            URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
            ossClient.shutdown();
            return url.toString();
        } catch (ClientException e) {
            log.error("generateCourseDocumentDownLoadUrl error,fileName:{}", fileName, e);
            throw e;
        }
    }

    /**
     * 生成文件下载地址
     *
     * @param fileName       oss中文件的名字（包含路径）
     * @param expirationTime URL过期时间
     * @return 下载url地址
     */
    @Override
    public String generateFileDownLoadUrl(String fileName, long expirationTime) {
        return null;
    }

    /**
     * 生成文件下载地址
     *
     * @param bucketName bucketName
     * @param fileName   oss中文件的名字（包含路径）
     * @return 下载url地址
     */
    @Override
    public String generateFileDownLoadUrl(String bucketName, String fileName) {
        return generateFileDownLoadUrl(bucketName, fileName, ONE_HUNDRED_YEARS);
    }

    /**
     * 生成文件下载地址
     *
     * @param fileName oss中文件的名字（包含路径）
     * @return 下载url地址
     */
    @Override
    public String generateFileDownLoadUrl(String fileName) {
        return null;
    }


    /**
     * 授权上传
     *
     */
    @Override
    public String authorizeUpload(OssPathEnum ossPathEnum, String bucketName) {
        try {
            OSS ossClient = context.getBean(OSS.class);
            String ossHost = "http://" + bucketName + "." + endpoint;
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, ossPathEnum.getPath());

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String, Object> respMap = new LinkedHashMap<String, Object>();
            respMap.put("accessid", accessKey);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", ossPathEnum.getPath());
            respMap.put("host", ossHost);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            respMap.put("callback", base64CallbackBody);

            JSONObject result = new JSONObject(respMap);
            // System.out.println(ja1.toString());
            return result.toString();

        } catch (Exception e) {
            log.error("authorizeUpload error", e);
        }
        return null;
    }

    /**
     * 授权上传
     *
     * @param ossPathEnum oss存放路径
     */
    @Override
    public String authorizeUpload(OssPathEnum ossPathEnum) {
        return null;
    }

}
