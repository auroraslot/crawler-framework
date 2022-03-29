package com.aurora.meta.crawler.mapper;

import com.aurora.meta.crawler.entity.MetaTagSentenceRelationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author irony
 */
@Mapper
public interface MetaTagSentenceRelationMapper extends BaseMapper<MetaTagSentenceRelationDO> {

    /**
     * 批量插入
     * @param metaTagSentenceRelationDOList
     */
    int batchInsert(@Param("list") List<MetaTagSentenceRelationDO> metaTagSentenceRelationDOList);
}
