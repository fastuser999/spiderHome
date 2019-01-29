package com.ipl.spider.framework.processor;

import com.ipl.spider.framework.annotation.TaskExtend;
import com.ipl.spider.framework.config.ProcessorAutoConfiguration;
import com.ipl.spider.framework.processor.listener.extend.TaskListener;
import com.ipl.spider.framework.processor.support.ConstTag;
import com.ipl.spider.framework.task.Task;
import com.ipl.spider.framework.task.TaskQueue;
import com.ipl.spider.framework.task.TaskStrategy;
import com.ipl.spider.framework.task.cache.TaskCache;
import com.ipl.spider.framework.utils.LoggerUtils;
import com.ipl.spider.framework.utils.ReflectUtils;
import com.ipl.spider.framework.utils.SpringContextHolder;

/**
 * @author dev
 * @desc 任务处理器
 */
public abstract class Processor extends LoggerUtils implements TaskListener {

    private static ProcessorAutoConfiguration conf = ProcessorAutoConfiguration.orElse(null);


    public Processor() {
    }

    /**
     * 设置处理器状态
     * @param status 可以是CLOSE和OPEN状态，一旦设置为CLOSE，则不再处理新的Task
     */
    public void setStatus(ConstTag status) {
        conf.setStatus(status);
    }

    /**
     * 生成任务
     * @return
     */

    public <T extends Task> T generateTask(TaskCache taskCache) {
        TaskQueue<Class<? extends Task>> taskQueue = taskCache.getTaskQueue();
        if (taskQueue == null || taskQueue.isEmpty()) {
            return null;
        }
        return (T) generateTask(taskQueue.poll(), taskCache);
    }

    /**
     *
     * @param c
     * @param <T>
     * @return
     */
    public abstract <T extends Task> T generateTask(Class<T> c, TaskCache taskCache);

    public static void addTask(String url, TaskStrategy taskStrategy) {
        TaskCache taskCache = new TaskCache(url, taskStrategy.getTaskQueue());
        taskCache.put("test", "1");
        Task task = taskStrategy.getProcessor().generateTask(taskCache);
        conf.getThreadPool().execute(task);
    }

    /**
     * 关闭处理器
     */
    public void shutDown() {
        conf.getThreadPool().shutdown();
    }

    public long getCount(ConstTag tag) {
        if (ConstTag.ACTIVE.equals(tag)) {
            return conf.getThreadPool().getActiveCount();
        }
        if (ConstTag.WAIT.equals(tag)) {
            return conf.getThreadPool().getTaskCount();
        }
        return conf.getThreadPool().getCompletedTaskCount();
    }

    public <T extends Task> T cloneTask() {
        return null;
    }

}
