package com.aurora.meta.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * meta_author
 * @author 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("meta_author")
public class MetaAuthorDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作者名
     */
    private String authorName;

    /**
     * 品类ID
     */
    private Integer categoryId;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 年代
     */
    private String era;

    /**
     * 主图URL
     */
    private String avatar;

    /**
     * bucketName
     */
    private String bucketName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * OSS存储路径
     */
    private String path;

    private static final long serialVersionUID = 1L;
}