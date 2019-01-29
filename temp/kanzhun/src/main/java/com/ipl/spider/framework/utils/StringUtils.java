package com.ipl.spider.framework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/8/12 14:22
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static String regexStr(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static List<String> regexStrList(String regex, String addition, String str) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            list.add(addition + matcher.group(1));
        }
        return list;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 从a标签中获取href
     * @param aTag a标签 <a href="aaa"></a>
     * @return 返回href值
     */
    public static String getHrefFromATag(String aTag) {
        String pattern= "href=\"([^\"]*)\"";
        Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
        Matcher m = p.matcher(aTag);
        if(m.find()) {
            aTag = m.group(1);
        }
        return aTag;
    }

    /**
     * 从html中获取节点的值
     * @param html html字符串
     * @return 返回text
     */
    public static String getTextFromHtml(String html) {

        String pattern= ">(.*)<";
        Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
        Matcher m = p.matcher(html);
        if(m.find()) {
            html = m.group(1);
        }
        return html;

    }

}
