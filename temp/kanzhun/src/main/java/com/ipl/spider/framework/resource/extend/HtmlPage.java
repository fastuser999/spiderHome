package com.ipl.spider.framework.resource.extend;

import com.ipl.spider.framework.resource.TempResource;
import com.ipl.spider.framework.utils.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/11/4 2:13
 */
public class HtmlPage extends TempResource<String> {

    public final static String TITLE = "TITLE";

    private Map<String, Object> cacheMap = new HashMap<>();

    private Document doc;

    public HtmlPage(String data) {
        super(data);
    }

    public HtmlPage(InputStream is) throws IOException {
        super(is);
    }

    @Override
    public void readData(InputStream is) throws IOException {
        this.readData(IOUtils.readTextFromStream(is));
    }

    @Override
    public byte[] getByte() {
        return getData().getBytes();
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(getByte());
    }

    private <T> T addToCache(String key, T o) {
        cacheMap.put(key, o);
        return o;
    }

    /**
     * 获取html标题
     * @return
     */
    public String getTitle() {
        if (cacheMap.containsKey(TITLE)) {
            return cacheMap.get(TITLE).toString();
        }
        if (doc == null) {
            doc = Jsoup.parse(getData());
        }
        Element e = doc.selectFirst("title");
        return e == null ? "" : addToCache(TITLE, e.text());
    }

    public Element selectFirst(String selector) {
        if (cacheMap.containsKey(selector)) {
            return (Element) cacheMap.get(selector);
        }
        return (Element) cacheMap.put(selector, this.doc.selectFirst(selector));
    }

    public Elements select(String selector) {
        if (cacheMap.containsKey(selector)) {
            return (Elements) cacheMap.get(selector);
        }
        return (Elements) cacheMap.put(selector, this.doc.select(selector));
    }

}
