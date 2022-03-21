package com.aurora.meta.crawler.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhonghuashishan
 */
public enum OssPathEnum {

    META_SPU_PATH(1, "spuPath", "spu图片OSS存放地址"),

    META_AUTHOR_PATH(2, "metaAuthorPath", "作者图片OSS存放地址");

    private Integer code;
    private String path;
    private String desc;

    OssPathEnum(Integer code, String path, String desc) {
        this.code = code;
        this.path = path;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>(16);
        for (OssPathEnum element : OssPathEnum.values()) {
            map.put(element.getCode(), element.getPath());
        }
        return map;
    }

    public static OssPathEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (OssPathEnum element : OssPathEnum.values()) {
            if (code.equals(element.getCode())) {
                return element;
            }
        }
        return null;
    }

}
