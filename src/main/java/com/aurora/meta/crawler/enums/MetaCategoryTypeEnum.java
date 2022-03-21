package com.aurora.meta.crawler.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 品类类型枚举
 *
 * @author irony
 */
public enum MetaCategoryTypeEnum {

    SPU_TYPE(0, "SPU品类"),

    AUTHOR_TYPE(1, "作者品类");

    private Integer code;
    private String value;

    MetaCategoryTypeEnum(Integer code, String value) {
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
        for (MetaCategoryTypeEnum element : MetaCategoryTypeEnum.values()) {
            map.put(element.getCode(), element.getValue());
        }
        return map;
    }

    public static MetaCategoryTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MetaCategoryTypeEnum element : MetaCategoryTypeEnum.values()) {
            if (code.equals(element.getCode())) {
                return element;
            }
        }
        return null;
    }
}
