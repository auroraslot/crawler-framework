package com.aurora.meta.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * meta_spu_info
 * @author 
 */
@Data
@TableName("meta_spu_info")
public class MetaSpuInfoDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu名
     */
    private String spuName;

    /**
     * 所属品类
     */
    private Integer categoryId;

    /**
     * 所属作者
     */
    private Integer authorId;

    private String avatar;

    private static final long serialVersionUID = 1L;
}