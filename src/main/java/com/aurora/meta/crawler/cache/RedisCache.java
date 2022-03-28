package com.aurora.meta.crawler.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author irony
 */
@Slf4j
public class RedisCache {

    private RedisTemplate redisTemplate;

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存存储
     *
     * @param key
     * @param value
     * @param seconds
     * @return void
     * @author zhonghuashishan
     */
    public void set(String key, String value, int seconds){
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
        if(seconds > 0){
            vo.set(key, value, seconds, TimeUnit.SECONDS);
        }else{
            vo.set(key, value);
        }
    }

    /**
     * 缓存获取
     *
     * @param key
     * @return java.lang.String
     * @author zhonghuashishan
     */
    public String get(String key){
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    /**
     * 缓存自增
     *
     * @param key
     * @return java.lang.String
     * @author zhonghuashishan
     */
    public Long incr(String key){
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
        return vo.increment(key);
    }
    /**
     * 缓存手动失效
     *
     * @param key
     * @return boolean
     * @author zhonghuashishan
     */
    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 批量缓存手动失效
     * @param keys
     * @return
     */
    public long delete(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 缓存存储并设置过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return void
     * @author zhonghuashishan
     */
    public void setex(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 缓存批量获取
     *
     * @param keyList
     * @return java.util.List
     * @author zhonghuashishan
     */
    public List mget(List<String> keyList) {
        List list = redisTemplate.opsForValue().multiGet(keyList);
        return (List) list.stream().filter(o -> Objects.nonNull(o)).collect(Collectors.toList());
    }

    /**
     * 删除key下的多个值
     *
     * @param key
     * @param values
     * @return void
     * @author zhonghuashishan
     */
    public void srem(String key, String[] values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 删除key下的多个值
     *
     * @param key
     * @param values
     * @return void
     * @author zhonghuashishan
     */
    public void sadd(String key, String[] values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 缓存成员获取
     *
     * @param key
     * @return java.util.Set
     * @author zhonghuashishan
     */
    public Set smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存成员是否存在
     *
     * @param key
     * @param member
     * @return java.lang.Boolean
     * @author zhonghuashishan
     */
    public Boolean sismember(String key, String member) {
        return redisTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 缓存有序区间值
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return java.util.Set
     * @author zhonghuashishan
     */
    public Set zrangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    /**
     * 缓存有序区间值
     *
     * @param key
     * @param min
     * @param max
     * @return java.util.Set
     * @author zhonghuashishan
     */
    public Set zrangeByScore2(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 缓存倒序排列指定区间值
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return java.util.Set
     * @author zhonghuashishan
     */
    public Set zrevrangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * 缓存有序存储
     *
     * @param key
     * @param member
     * @param score
     * @return java.lang.Boolean
     * @author zhonghuashishan
     */
    public Boolean zadd(String key, String member, double score) {
        return redisTemplate.opsForZSet().add(key, member, score);
    }

    /**
     * 缓存有序存储
     *
     * @param key
     * @param values
     * @return java.lang.Long
     * @author zhonghuashishan
     */
    public Long zremove(String key, String[] values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 缓存有序数量
     *
     * @param key
     * @return java.lang.Long
     * @author zhonghuashishan
     */
    public Long zcard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 缓存过期时间
     * @param key
     * @param expire
     * @return
     */
    public Boolean expire(String key, Long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * list类型数据 lpush
     */
    public Long lpush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * list类型数据 ltrim
     */
    public void ltrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * list类型数据 lrange
     */
    public List<String> lrange(String key, long start, long end) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, start, end);
    }

    /**
     * 以list集合的形式添加数据
     *
     * @param key
     * @param values
     * @return
     */
    public Long lPushAll(String key, String... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 以list集合的形式添加数据
     *
     * @param key
     * @param values
     * @return
     */
    public Long lPushAll(String key, List<String> values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 以list集合的形式添加数据
     *
     * @param key
     * @param values
     * @return
     */
    public Long rPushAll(String key, String... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 以list集合的形式添加数据
     *
     * @param key
     * @param values
     * @return
     */
    public Long rPushAll(String key, List<String> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 返回list集合下表区间的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 返回list集合下所有的元素
     * @param key
     * @return
     */
    public List<String> lRangeAll(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 返回list集合的大小
     *
     * @param key
     * @return
     */
    public Long lsize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * scan匹配符合条件的Key
     * @param match
     * @return
     */
    public Set<String> scan(String match, int count) {
        return (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match("*" + match + "*").count(count).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });
    }

    /**
     * 批量设置缓存
     * @param map  key为缓存的key，value为缓存值
     * @return
     */
    public void mset(Map map){
        if (null == map || map.isEmpty()){
            return ;
        }
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 批量设置缓存
     * @param map  key为缓存的key，value为缓存值
     * @return
     */
    public void msetIfAbsent(Map map){
        if (null == map || map.isEmpty()){
            return ;
        }
        redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 判读key是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(String key){
        if (StringUtils.isBlank(key)){
            return false;
        }
        return redisTemplate.hasKey(key);
    }
}
