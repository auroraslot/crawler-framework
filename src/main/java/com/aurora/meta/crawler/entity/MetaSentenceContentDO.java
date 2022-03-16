package com.aurora.meta.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * meta_sentence_content
 * @author 
 */
@Data
@TableName("meta_sentence_content")
public class MetaSentenceContentDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 句子信息ID
     */
    private Long sentenceId;

    /**
     * 句子内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}