package com.aurora.meta.crawler.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 是否枚举
 *
 * @author irony
 */
public enum YesOrNoEnum {

    /**
     * 是
     */
    YES(1, "是"),

    /**
     * 否
     */
    NO(0, "否");

    private Integer code;

    private String value;

    YesOrNoEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static Map<Integer, String> toMap(){
        Map<Integer, String> map = new HashMap<>(16);
        for (YesOrNoEnum element : YesOrNoEnum.values() ){
            map.put(element.getCode(),element.getValue());
        }
        return map;
    }

    public static YesOrNoEnum getByCode(Integer code){
        if(code == null) {
            return null;
        }
        for(YesOrNoEnum element : YesOrNoEnum.values()){
            if (code.equals(element.getCode())) {
                return element;
            }
        }
        return null;
    }

}