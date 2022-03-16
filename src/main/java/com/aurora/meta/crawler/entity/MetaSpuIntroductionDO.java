package com.aurora.meta.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * meta_spu_introduction
 * @author 
 */
@Data
@TableName("meta_spu_introduction")
public class MetaSpuIntroductionDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu信息主键
     */
    private Long spuId;

    /**
     * 简介内容
     */
    private String introduction;

    private static final long serialVersionUID = 1L;
}