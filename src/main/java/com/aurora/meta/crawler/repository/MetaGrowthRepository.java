package com.aurora.meta.crawler.repository;

import com.aurora.meta.crawler.entity.*;
import com.aurora.meta.crawler.mapper.*;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author irony
 */
@Slf4j
public class MetaGrowthRepository {
    private MetaAuthorMapper metaAuthorMapper;

    private MetaAuthorIntroductionMapper metaAuthorIntroductionMapper;

    private MetaCategoryMapper metaCategoryMapper;

    private MetaSentenceInfoMapper metaSentenceInfoMapper;

    private MetaSentenceContentMapper metaSentenceContentMapper;

    private MetaSpuInfoMapper metaSpuInfoMapper;

    private MetaSpuIntroductionMapper metaSpuIntroductionMapper;

    private MetaTagMapper metaTagMapper;

    private MetaTagSentenceRelationMapper metaTagSentenceRelationMapper;

    public MetaGrowthRepository(MetaAuthorMapper metaAuthorMapper, MetaAuthorIntroductionMapper metaAuthorIntroductionMapper, MetaCategoryMapper metaCategoryMapper, MetaSentenceInfoMapper metaSentenceInfoMapper, MetaSentenceContentMapper metaSentenceContentMapper, MetaSpuInfoMapper metaSpuInfoMapper, MetaSpuIntroductionMapper metaSpuIntroductionMapper, MetaTagMapper metaTagMapper, MetaTagSentenceRelationMapper metaTagSentenceRelationMapper) {
        this.metaAuthorMapper = metaAuthorMapper;
        this.metaAuthorIntroductionMapper = metaAuthorIntroductionMapper;
        this.metaCategoryMapper = metaCategoryMapper;
        this.metaSentenceInfoMapper = metaSentenceInfoMapper;
        this.metaSentenceContentMapper = metaSentenceContentMapper;
        this.metaSpuInfoMapper = metaSpuInfoMapper;
        this.metaSpuIntroductionMapper = metaSpuIntroductionMapper;
        this.metaTagMapper = metaTagMapper;
        this.metaTagSentenceRelationMapper = metaTagSentenceRelationMapper;
    }

    public void insert(MetaAuthorDO metaAuthor) {
        metaAuthorMapper.insert(metaAuthor);
    }

    public void insert(MetaAuthorIntroductionDO metaAuthorIntroduction) {
        metaAuthorIntroductionMapper.insert(metaAuthorIntroduction);
    }

    public void insert(MetaCategoryDO metaCategory) {
        metaCategoryMapper.insert(metaCategory);
    }

    public void insert(MetaSentenceInfoDO metaSentenceInfo) {
        metaSentenceInfoMapper.insert(metaSentenceInfo);
    }

    public void insert(MetaSentenceContentDO metaSentenceContent) {
        metaSentenceContentMapper.insert(metaSentenceContent);
    }

    public void insert(MetaSpuInfoDO metaSpuInfo) {
        metaSpuInfoMapper.insert(metaSpuInfo);
    }

    public void insert(MetaSpuIntroductionDO metaSpuIntroduction) {
        metaSpuIntroductionMapper.insert(metaSpuIntroduction);
    }

    public void insert(MetaTagDO metaTag) {
        metaTagMapper.insert(metaTag);
    }

    public void insert(MetaTagSentenceRelationDO metaTagSentenceRelation) {
        metaTagSentenceRelationMapper.insert(metaTagSentenceRelation);
    }

    public MetaSpuInfoDO querySpuInfo(String spuName) {
        LambdaQueryWrapper<MetaSpuInfoDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MetaSpuInfoDO::getSpuName, spuName);
        queryWrapper.select(MetaSpuInfoDO::getId);
        return metaSpuInfoMapper.selectOne(queryWrapper);
    }
}
