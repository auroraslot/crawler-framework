package com.aurora.meta.crawler.repository;

import com.alibaba.fastjson.JSON;
import com.aurora.meta.crawler.constants.RedisKeyConstant;
import com.aurora.meta.crawler.entity.*;
import com.aurora.meta.crawler.mapper.*;
import com.aurora.meta.crawler.repository.cache.MetaGrowthCache;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static com.aurora.meta.crawler.constants.RedisKeyConstant.SENTENCE_CONTENT;

/**
 * @author irony
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
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

    private MetaGrowthCache metaGrowthCache;

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
        metaGrowthCache.insert(metaSentenceContent);
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

    /**
     * 填充SPU的categoryId字段
     * @param id
     * @param categoryId
     */
    public void fillSpuCategory(Long id, Integer categoryId) {
        LambdaUpdateWrapper<MetaSpuInfoDO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(MetaSpuInfoDO::getCategoryId, categoryId);
        updateWrapper.eq(MetaSpuInfoDO::getId, id);
        metaSpuInfoMapper.update(null, updateWrapper);
    }

    public MetaAuthorDO queryAuthorInfo(String authorName) {
        LambdaQueryWrapper<MetaAuthorDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MetaAuthorDO::getAuthorName, authorName);
        queryWrapper.select(MetaAuthorDO::getId);
        return metaAuthorMapper.selectOne(queryWrapper);
    }

    @Deprecated
    public MetaSentenceContentDO querySentence(String sentenceContent) {
        LambdaQueryWrapper<MetaSentenceContentDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MetaSentenceContentDO::getContent, sentenceContent);
        queryWrapper.select(MetaSentenceContentDO::getSentenceId);
        return metaSentenceContentMapper.selectOne(queryWrapper);
    }

    /**
     * 填充sentence的spuId字段
     * @param id
     * @param spuId
     */
    public void fillSentenceSpu(Long id, Long spuId) {
        LambdaUpdateWrapper<MetaSentenceInfoDO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(MetaSentenceInfoDO::getSpuId, spuId);
        updateWrapper.eq(MetaSentenceInfoDO::getId, id);
        metaSentenceInfoMapper.update(null, updateWrapper);
    }


    public List<MetaAuthorDO> queryMetaAuthor() {
        // 构造查询条件
        LambdaQueryWrapper<MetaAuthorDO> queryWrapper = Wrappers.lambdaQuery();
        // 分页查询存放数据的总集合
        List<MetaAuthorDO> results = new ArrayList<>();

        int pageNum = 1;
        // 设置每次查询的数据量，最大为1000
        int pageSize = 1000;
        Page<MetaAuthorDO> page = new Page<>(pageNum, pageSize);
        // 查询第一页数据
        Page<MetaAuthorDO> pageResult = metaAuthorMapper.selectPage(page, queryWrapper);

        // 判断是否还有数据，还有数据则页码+1继续执行分页查询
        List<MetaAuthorDO> batchResult = pageResult.getRecords();
        try {
            while (batchResult.size() >= pageSize) {
                results.addAll(batchResult);
                pageNum += 1;
                page.setCurrent(pageNum);
                pageResult = metaAuthorMapper
                        .selectPage(page, queryWrapper);
                batchResult = pageResult.getRecords();
                // 每次循环获取数据后，休眠20ms，避免对数据库造成太大压力
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("SQL异常, {}", e);
        }

        // 最后一组数据也放入结果集中
        results.addAll(page.getRecords());
        return results;
    }


    public List<MetaSpuInfoDO> queryMetaSpu() {
        // 构造查询条件
        LambdaQueryWrapper<MetaSpuInfoDO> queryWrapper = Wrappers.lambdaQuery();
        // 分页查询存放数据的总集合
        List<MetaSpuInfoDO> results = new ArrayList<>();

        int pageNum = 1;
        // 设置每次查询的数据量，最大为1000
        int pageSize = 1000;
        Page<MetaSpuInfoDO> page = new Page<>(pageNum, pageSize);
        // 查询第一页数据
        Page<MetaSpuInfoDO> pageResult = metaSpuInfoMapper.selectPage(page, queryWrapper);

        // 判断是否还有数据，还有数据则页码+1继续执行分页查询
        List<MetaSpuInfoDO> batchResult = pageResult.getRecords();
        try {
            while (batchResult.size() >= pageSize) {
                results.addAll(batchResult);
                pageNum += 1;
                page.setCurrent(pageNum);
                pageResult = metaSpuInfoMapper
                        .selectPage(page, queryWrapper);
                batchResult = pageResult.getRecords();
                // 每次循环获取数据后，休眠20ms，避免对数据库造成太大压力
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("SQL异常, {}", e);
        }

        // 最后一组数据也放入结果集中
        results.addAll(page.getRecords());
        return results;
    }

    public Map<String, String> queryMetaSentence() {
        // 构造查询条件
        LambdaQueryWrapper<MetaSentenceContentDO> queryWrapper = Wrappers.lambdaQuery();
        // 分页查询存放数据的总集合
//        List<MetaSentenceContentDO> results = new ArrayList<>();
        Map<String, String> results = new HashMap<>();

        int pageNum = 1;
        // 设置每次查询的数据量，最大为1000
        int pageSize = 10000;
        Page<MetaSentenceContentDO> page = new Page<>(pageNum, pageSize);
        // 查询第一页数据
        Page<MetaSentenceContentDO> pageResult = metaSentenceContentMapper.selectPage(page, queryWrapper);

        // 判断是否还有数据，还有数据则页码+1继续执行分页查询
        List<MetaSentenceContentDO> batchResult = pageResult.getRecords();
        while (batchResult.size() >= pageSize) {
            putAll(results, batchResult);
            pageNum += 1;
            page.setCurrent(pageNum);
            pageResult = metaSentenceContentMapper
                    .selectPage(page, queryWrapper);
            batchResult = pageResult.getRecords();
            // 每次循环获取数据后，休眠20ms，避免对数据库造成太大压力
//                Thread.sleep(20);
        }

        // 最后一组数据也放入结果集中
        putAll(results, page.getRecords());
        return results;
    }


    private void putAll(Map<String, String> results, List<MetaSentenceContentDO> batchResult) {
        Map<String, String> maps = batchResult.stream()
                .collect(Collectors.toMap(meta -> {
                            String uniqueCode = meta.getSentenceId() + meta.getContent();
                            return SENTENCE_CONTENT + uniqueCode.hashCode();
                        },
                    JSON::toJSONString));
        results.putAll(maps);
    }
}
