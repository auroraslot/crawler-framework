package com.aurora.meta.crawler.config;

import com.aurora.meta.crawler.core.RunningMocker;
import com.aurora.meta.crawler.mapper.*;
import com.aurora.meta.crawler.repository.MetaGrowthRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author irony
 */
@Configuration
public class MetaCrawlerAutoConfiguration {

    @Resource
    private MetaAuthorMapper metaAuthorMapper;

    @Resource
    private MetaAuthorIntroductionMapper metaAuthorIntroductionMapper;

    @Resource
    private MetaCategoryMapper metaCategoryMapper;

    @Resource
    private MetaSentenceInfoMapper metaSentenceInfoMapper;

    @Resource
    private MetaSentenceContentMapper metaSentenceContentMapper;

    @Resource
    private MetaSpuInfoMapper metaSpuInfoMapper;

    @Resource
    private MetaSpuIntroductionMapper metaSpuIntroductionMapper;

    @Resource
    private MetaTagMapper metaTagMapper;

    @Resource
    private MetaTagSentenceRelationMapper metaTagSentenceRelationMapper;

    @Bean
    public MetaGrowthRepository metaGrowthRepository() {
        MetaGrowthRepository metaGrowthRepository = new MetaGrowthRepository(metaAuthorMapper,
                metaAuthorIntroductionMapper,
                metaCategoryMapper,
                metaSentenceInfoMapper,
                metaSentenceContentMapper,
                metaSpuInfoMapper,
                metaSpuIntroductionMapper,
                metaTagMapper,
                metaTagSentenceRelationMapper);
        return metaGrowthRepository;
    }

}
