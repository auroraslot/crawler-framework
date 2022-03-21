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
 * meta_sentence_info
 * @author 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("meta_sentence_info")
public class MetaSentenceInfoDO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spuID
     */
    private Long spuId;

    /**
     * 作者ID
     */
    private Integer authorId;

    /**
     * 喜欢数量
     */
    private Integer likeNumber;

    /**
     * 评论数量
     */
    private Integer commentNumber;

    private static final long serialVersionUID = 1L;
}