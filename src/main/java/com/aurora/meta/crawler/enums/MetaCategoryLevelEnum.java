package com.aurora.meta.crawler.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 品类级别枚举
 *
 * @author irony
 */
public enum MetaCategoryLevelEnum {

    PRIMARY_LEVEL(1, "一级品类"),

    SECONDARY_LEVEL(2, "二级品类"),

    THREE_LEVEL(3, "三级品类");

    private Integer code;
    private String value;

    MetaCategoryLevelEnum(Integer code, String value) {
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
        for (MetaCategoryLevelEnum element : MetaCategoryLevelEnum.values()) {
            map.put(element.getCode(), element.getValue());
        }
        return map;
    }

    public static MetaCategoryLevelEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MetaCategoryLevelEnum element : MetaCategoryLevelEnum.values()) {
            if (code.equals(element.getCode())) {
                return element;
            }
        }
        return null;
    }
}
