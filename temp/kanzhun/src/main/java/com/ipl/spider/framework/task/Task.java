package com.ipl.spider.framework.task;

import com.ipl.spider.framework.annotation.TaskExtend;
import com.ipl.spider.framework.config.AppConfig;
import com.ipl.spider.framework.processor.Processor;
import com.ipl.spider.framework.processor.listener.extend.NullListener;
import com.ipl.spider.framework.task.cache.TaskCache;
import com.ipl.spider.framework.task.support.BatchTask;
import com.ipl.spider.framework.utils.ReflectUtils;
import com.ipl.spider.framework.utils.SpringContextHolder;
import com.ipl.spider.framework.utils.StringUtils;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;

import java.util.Queue;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/8/12 14:17
 */
public interface Task extends Runnable {

    String uuid = StringUtils.getUUID();

    AppConfig config = SpringContextHolder.getBean(AppConfig.class);

    default void setUrl(String url) {

    }

    default String getUrl() {
        return "";
    }

    default String getUUID() {
        return uuid;
    }

    default Class<?> getTaskType() {
        return this.getClass();
    }

    default TaskCache getTaskCache() {
        return null;
    }

    /**
     *
     * @param taskStrategy
     * @return
     */
    static Task generateTask(TaskStrategy taskStrategy) {
        TaskCache taskCache = new TaskCache();
        //taskCache.put(TaskCache.ExtendKey.TASK_URL.getKey(), taskStrategy.getTaskUrl());
        //taskCache.put(TaskCache.ExtendKey.TASK_QUEUE.getKey(), taskStrategy.getTaskList());
        return generateTask(taskCache);
    }

    static Task generateTask(TaskCache taskCache) {
        Queue<Class<? extends BatchTask>> taskStrategy = (Queue<Class<? extends BatchTask>>) taskCache.get(TaskCache.ExtendKey.TASK_QUEUE.getKey());
        if (taskStrategy == null || taskStrategy.isEmpty()) {
            return null;
        }
        Class<? extends Task> c = (Class<? extends Task>) taskStrategy.poll();
        Class<? extends Processor> taskListener = ReflectUtils.getTypeAnnotation(c, TaskExtend.class).processor();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(c);
        enhancer.setCallbackFilter(method -> method.getName().equals("run") ? 1 : 0);
        enhancer.setCallbacks(new Callback[]{new NullListener(), SpringContextHolder.getBean(taskListener)});
        return (Task) enhancer.create(new Class[]{TaskCache.class}, new Object[]{taskCache});
    }

}
