package com.aurora.meta.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author irony
 */
public class RegexUtil {
    public static String extract(String target, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
