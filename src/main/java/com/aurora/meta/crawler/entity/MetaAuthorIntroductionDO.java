package com.aurora.meta.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * meta_author_introduction
 * @author 
 */
@Data
@TableName("meta_author_introduction")
public class MetaAuthorIntroductionDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 作者ID
     */
    private Integer authorId;

    /**
     * 简介内容
     */
    private String introduction;

    private static final long serialVersionUID = 1L;
}