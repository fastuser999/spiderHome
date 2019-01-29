package com.ipl.spider.framework.task.support.extend;

import com.ipl.spider.framework.annotation.Setter;
import com.ipl.spider.framework.task.cache.TaskCache;
import com.ipl.spider.framework.annotation.Getter;
import com.ipl.spider.framework.resource.Resource;
import com.ipl.spider.framework.task.support.BatchTask;
import com.ipl.spider.framework.task.support.HttpSupport;

import java.net.URLConnection;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/12/6 23:15
 */
public abstract class HttpBatchTask<T extends URLConnection, R extends Resource<?>> extends BatchTask implements HttpSupport<T, R> {

    public HttpBatchTask(TaskCache taskCache) {
        super(taskCache);
    }

}
