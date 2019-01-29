package com.ipl.spider.framework.task.support;


import com.ipl.spider.framework.task.Task;
import com.ipl.spider.framework.task.cache.TaskCache;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/10/27 21:22
 */
public abstract class BatchTask implements Task {

    private String url;

    private TaskCache taskCache;

    public BatchTask(TaskCache taskCache) {
        this.taskCache = taskCache;
    }

    @Override
    public TaskCache getTaskCache() {
        return taskCache;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return null;
    }
}
