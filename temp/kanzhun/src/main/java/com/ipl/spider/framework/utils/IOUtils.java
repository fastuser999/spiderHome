package com.ipl.spider.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/11/5 23:46
 */
public class IOUtils {

    private static Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static String readTextFromStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
        } catch (Exception e) {
            logger.error("从流中读取文本失败:{}", e.getLocalizedMessage());
        }
        return sb.toString();
    }

    public static List<String> readListFromStream(InputStream is) {
        List<String> resultList = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                resultList.add(line);
            }
        } catch (Exception e) {
            logger.error("从流中读取bean失败:{}", e.getLocalizedMessage());
        }
        return resultList;
    }

}
