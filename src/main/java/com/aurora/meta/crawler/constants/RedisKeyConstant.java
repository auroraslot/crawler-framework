package com.aurora.meta.crawler.constants;

/**
 * redis key 常量
 * @author irony
 */
public class RedisKeyConstant {

    /**
     * 句子key常量
     *
     * key：
     *      sentence_content:hash_code
     * string-value：
     *      {'sentenceId': 1, 'content': '如果你不知道自己想去哪的话，你就不会到达。'}
     */
    public static final String SENTENCE_CONTENT = "sentence_content:";

}
