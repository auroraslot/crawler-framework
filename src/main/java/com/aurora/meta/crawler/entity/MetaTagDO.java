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
 * meta_tag
 * @author 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("meta_tag")
public class MetaTagDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签分类：0-其他分类标签；1-感觉分类标签；2-长度分类标签
     */
    private Integer tagClassify;

    /**
     * 溯源站点
     */
    private String traceSource;

    /**
     * 溯源站点名称
     */
    private String traceSourceTitle;

    private static final long serialVersionUID = 1L;
}