package com.aurora.meta.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * meta_tag_sentence_relation
 * @author 
 */
@Data
@TableName("meta_tag_sentence_relation")
public class MetaTagSentenceRelationDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签ID
     */
    private Integer tagId;

    /**
     * 句子内容
     */
    private String sentenceContent;

    private static final long serialVersionUID = 1L;
}