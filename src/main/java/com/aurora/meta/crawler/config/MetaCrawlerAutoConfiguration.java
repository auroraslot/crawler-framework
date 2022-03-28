package com.aurora.meta.crawler.config;

import com.aurora.meta.crawler.mapper.*;
import com.aurora.meta.crawler.repository.MetaGrowthRepository;
import com.aurora.meta.crawler.repository.cache.MetaGrowthCache;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @author irony
 */
@Data
@Configuration
@Import(value = {RedisConfig.class})
@EnableConfigurationProperties({AliyunConfig.class, MetaCrawlerProperties.class})
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
    @ConditionalOnClass(MetaGrowthCache.class)
    public MetaGrowthRepository metaGrowthRepository(MetaGrowthCache metaGrowthCache) {
        MetaGrowthRepository metaGrowthRepository = new MetaGrowthRepository(metaAuthorMapper,
                metaAuthorIntroductionMapper,
                metaCategoryMapper,
                metaSentenceInfoMapper,
                metaSentenceContentMapper,
                metaSpuInfoMapper,
                metaSpuIntroductionMapper,
                metaTagMapper,
                metaTagSentenceRelationMapper,
                metaGrowthCache);
        return metaGrowthRepository;
    }
}
