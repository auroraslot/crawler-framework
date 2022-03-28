package com.aurora.meta.crawler.repository.cache;

import com.alibaba.fastjson.JSON;
import com.aurora.meta.crawler.cache.RedisCache;
import com.aurora.meta.crawler.entity.MetaSentenceContentDO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.aurora.meta.crawler.constants.RedisKeyConstant.SENTENCE_CONTENT;

/**
 * @author irony
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class MetaGrowthCache {

    private RedisCache redisCache;

    public MetaSentenceContentDO querySentenceFromCache(String sentenceContent) {
        if (StringUtils.isEmpty(sentenceContent)) {
            return null;
        }

        String redisKey = SENTENCE_CONTENT + DigestUtils.md5Hex(sentenceContent);
        String metaSentenceContentJson = redisCache.get(redisKey);
        if (StringUtils.isEmpty(metaSentenceContentJson)) {
            return null;
        }

        return JSON.parseObject(metaSentenceContentJson, MetaSentenceContentDO.class);
    }

    public void insert(MetaSentenceContentDO metaSentenceContentDO) {
        String redisKey = SENTENCE_CONTENT + DigestUtils.md5Hex(metaSentenceContentDO.getContent());
        String metaSentenceContentJson = JSON.toJSONString(metaSentenceContentDO);
        redisCache.set(redisKey, metaSentenceContentJson, -1);
    }

    public void batchInsert(Map<String, String> map) {
        redisCache.mset(map);
    }

}
