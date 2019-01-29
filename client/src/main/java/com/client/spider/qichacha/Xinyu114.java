package com.client.spider.qichacha;

import org.apache.http.client.CookieStore;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Xinyu114 implements PageProcessor {

    private static final String DOOR = "http://www.xinyu114.com/";
    private static final String DOMAIN ="www.xinyu114.com";
    private static final int THREAD_NUM = 100;
    private static final String ROOT = "http://www.xinyu114.com";
    // 隧道代理订单appKey(请注意替换)
    String appKey  = "RkJrTmhCWjdyVVNOa3F4TDpZVTN1T2RpRFJQN0NhZml3";
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(2).setSleepTime(1000)
            .setDomain(DOMAIN);
    private CookieStore cookieStore;

    public void process(Page page) {
        String origin = page.getUrl().toString();
        System.out.println("解析开始 ==> " +origin);
        //System.out.println("content ==> " + page.getRawText());
        page.addTargetRequests(page.getHtml().links().regex("http://www\\.xinyu114\\.com/company.*").all());
    }

    public Site getSite() {
        return site;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public static void main(String[] args) {
        //
        final Xinyu114 qiChaChaD = new Xinyu114();
        //模拟请求获取cookie

        final HttpClientDownloader httpClientDownloader = new HttpClientDownloader();

        //爬取数据

        Spider spider = Spider.create( new Xinyu114())
                .addUrl(DOOR)
                .addPipeline(new ConsolePipeline())
                .setDownloader(httpClientDownloader)
                .thread(THREAD_NUM);
        spider.run();
    }
}
