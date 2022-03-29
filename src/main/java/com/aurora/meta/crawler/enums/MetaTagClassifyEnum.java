package com.aurora.meta.crawler.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 标签分类：0-其他分类标签；1-感觉分类标签；2-长度分类标签
 * @author irony
 */
public enum MetaTagClassifyEnum {

    OTHER(0, "其他分类"),

    FEEL(1, "感觉分类"),
    
    LENGTH(2, "长度分类");

    private Integer code;
    private String value;

    MetaTagClassifyEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>(16);
        for (MetaTagClassifyEnum element : MetaTagClassifyEnum.values()) {
            map.put(element.getCode(), element.getValue());
        }
        return map;
    }

    public static MetaTagClassifyEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MetaTagClassifyEnum element : MetaTagClassifyEnum.values()) {
            if (code.equals(element.getCode())) {
                return element;
            }
        }
        return null;
    }
    
}
