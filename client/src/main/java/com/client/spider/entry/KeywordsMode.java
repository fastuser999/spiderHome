package com.client.spider.entry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.client.spider.PageProcessor.qichachaSearch;
import com.client.spider.util.PropertiesReader;

/**
 * Created by user on 2019/1/11.
 */
public class KeywordsMode {

    private static Logger logger = LoggerFactory.getLogger(KeywordsMode.class);

    public static void main(String[] args) {
        KeywordsMode mode = new KeywordsMode();
        mode.runThreads();
    }

    private int thread_cnt = 200;
    private ExecutorService executorService = null;
    public AtomicInteger total = new AtomicInteger(0);
    public AtomicInteger used = new AtomicInteger(0);

    public KeywordsMode() {
        thread_cnt = PropertiesReader.instance().getOrDefault("thread.cnt", 200);
        executorService = Executors.newFixedThreadPool(thread_cnt);
    }

    public void runThreads() {
        while (true) {
            JSONObject ret = getIds();
            
            try {
            	if (ret == null) {
            		Thread.sleep(2000);
                	continue;
                }
            	
                long start = Long.parseLong(ret.get("start").toString());
                long end = Long.parseLong(ret.get("end").toString());
                total.addAndGet((int)(end - start));
                for (long i = start; i < end; ++i) {
                    executorService.execute(new Task(i));
                }
                logger.info("total:{}, used:{}", total.get(), used.get());
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(ex.getMessage());
            }

            while (total.get() - used.get() >= thread_cnt * 2) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private JSONObject getIds(){
        //get请求返回结果
        JSONObject jsonResult = null;
        final String url = PropertiesReader.instance().getProperty("remote")  + "range";
        try {
            HttpClient client = HttpClientBuilder.create().build();;
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.parseObject(strResult);
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (Exception e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    public class Task implements Runnable {
        private long id;
        public Task(long id) {
            this.id = id;
        }
        @Override
        public void run() {
            used.incrementAndGet();
            if (!(new qichachaSearch()).run(id)) {
            	logger.info("id:{} need re run for vpn problem", id);
            	executorService.execute(new Task(id));
            	total.incrementAndGet();
            }
        }
    }
}
