package com.ipl.spider.framework.task.cache;

import com.ipl.spider.framework.task.Task;
import com.ipl.spider.framework.task.TaskQueue;

import java.util.HashMap;


/**
 * @author dev
 */
public class TaskCache extends HashMap<String, Object> {

    private String uuid;

    private String scope;

    private String url;

    private TaskQueue<Class<? extends Task>> taskQueue;

    public TaskCache() {
    }

    public TaskCache(String key, String value) {
        this.put(key, value);
    }

    public TaskCache(String url, TaskQueue<Class<? extends Task>> taskQueue) {
        this.url = url;
        this.taskQueue = taskQueue;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TaskQueue<Class<? extends Task>> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(TaskQueue<Class<? extends Task>> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public enum ExtendKey {
        TASK_URL("url", "任务URL"),
        TASK_QUEUE("taskQueue", "任务策略队列，创建批量任务使用");

        private String key;

        private String desc;

        ExtendKey(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public String getKey() {
            return key;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return "ExtendKey{" +
                    "key='" + key + '\'' +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

}
