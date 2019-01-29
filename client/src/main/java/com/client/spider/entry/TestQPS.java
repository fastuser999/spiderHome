package com.client.spider.entry;

import com.alibaba.fastjson.JSONObject;
import com.client.spider.PageProcessor.qichachaSearch;
import com.client.spider.util.DBHelper;
import com.client.spider.util.PropertiesReader;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user on 2019/1/11.
 */
public class TestQPS {

    private static Logger logger = LoggerFactory.getLogger(TestQPS.class);

    public static void main(String[] args) {
        logger.info("start qps");
        TestQPS mode = new TestQPS();
        mode.runThreads();
    }

    private int thread_cnt = 200;
    private ExecutorService executorService = null;
    public AtomicInteger total = new AtomicInteger(0);
    public AtomicInteger used = new AtomicInteger(0);

    public TestQPS() {
        thread_cnt = 300;//PropertiesReader.instance().getOrDefault("thread.cnt", 200);
        executorService = Executors.newFixedThreadPool(thread_cnt);
    }

    public void runThreads() {
        for (long i = 0; i < thread_cnt; ++i) {
            executorService.execute(new Task(i));
        }
    }

    public class Task implements Runnable {
        private long id;
        public Task(long id) {
            this.id = id;
        }
        @Override
        public void run() {
            DBHelper mysqlConn = null;
            try {
                String url = PropertiesReader.instance().getProperty("spring.datasource.url");
                String name = PropertiesReader.instance().getProperty("spring.datasource.driverClassName");
                String user = PropertiesReader.instance().getProperty("spring.datasource.username");
                String password = PropertiesReader.instance().getProperty("spring.datasource.password");
                mysqlConn = new DBHelper(url, name, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while(true) {
                int cnt = used.incrementAndGet();
                if (cnt % 10000 == 0) {
                    logger.info("cnt={}", cnt);
                }

                String sql = String.format("insert into test(name, age, area) values ('sssfd', %d, 'dff')", cnt);
                try {
                    mysqlConn.executeNonQuery(sql);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        }
    }
}
