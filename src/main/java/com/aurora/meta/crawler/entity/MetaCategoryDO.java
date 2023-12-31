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
 * meta_category
 * @author 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("meta_category")
public class MetaCategoryDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 品类名
     */
    private String categoryName;

    /**
     * 品类类型：0-spu品类；1-作者品类
     */
    private Integer categoryType;

    /**
     * 父品类ID
     */
    private Integer parentId;

    /**
     * 是否是叶子类目：0-不是；1-是
     */
    private Integer lastCategory;

    /**
     * 品类级别
     */
    private Integer categoryLevel;

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