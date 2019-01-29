package com.ipl.spider.framework.utils;
import java.util.Collection;
import java.util.Map;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/10/22 23:39
 */
public class PubTools {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && ((String) obj).length() == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        return false;
    }

}
