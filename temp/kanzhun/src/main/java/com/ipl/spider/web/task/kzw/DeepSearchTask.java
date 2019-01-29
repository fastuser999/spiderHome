package com.ipl.spider.web.task.kzw;

import com.ipl.spider.framework.annotation.Getter;
import com.ipl.spider.framework.resource.extend.HtmlPage;
import com.ipl.spider.framework.task.cache.TaskCache;
import com.ipl.spider.framework.task.support.extend.HttpBatchTask;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author dev
 */
public class DeepSearchTask extends HttpBatchTask<HttpsURLConnection, HtmlPage> {


    @Getter
    private String test;

    public DeepSearchTask(TaskCache taskCache) {
        super(taskCache);
    }

    @Override
    public void run() {

    }

    @Override
    public HtmlPage recv() throws Exception {
        return null;
    }
}
